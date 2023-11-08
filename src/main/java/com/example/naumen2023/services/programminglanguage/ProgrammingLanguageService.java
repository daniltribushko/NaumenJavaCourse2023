package com.example.naumen2023.services.programminglanguage;

import com.example.naumen2023.models.entities.*;
import com.example.naumen2023.models.enums.*;
import com.example.naumen2023.models.gson.*;
import com.example.naumen2023.repositories.AreaRepository;
import com.example.naumen2023.repositories.EmployerRepository;
import com.example.naumen2023.repositories.ProgrammingLanguageRepository;
import com.example.naumen2023.repositories.VacancyHHruRepository;
import com.example.naumen2023.services.github.GitHubParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Сервис для работы с сущностями языков программирования
 *
 * @author Tribushko Danil
 * @since 30.10.2023
 */
@Slf4j
@Service
public class ProgrammingLanguageService{
    private ProgrammingLanguageRepository programmingLanguageRepository;
    private VacancyHHruRepository vacancyHHruRepository;
    private AreaRepository areaRepository;
    private EmployerRepository employerRepository;

    @Autowired
    public ProgrammingLanguageService(ProgrammingLanguageRepository programmingLanguageRepository,
                                      VacancyHHruRepository vacancyHHruRepository, AreaRepository areaRepository,
                                      EmployerRepository employerRepository) {
        this.programmingLanguageRepository = programmingLanguageRepository;
        this.vacancyHHruRepository = vacancyHHruRepository;
        this.areaRepository = areaRepository;
        this.employerRepository = employerRepository;
    }

    /**
     * Обновление значения количества репозиториев
     *
     * @param programmingLanguageName название языка программирования
     */
    public void updateCountRepositories(ProgrammingLanguageName programmingLanguageName) throws MalformedURLException,
            InterruptedException {
        //Получаем сущность языка программирования
        ProgrammingLanguageEntity programmingLanguage = programmingLanguageRepository
                .findByName(programmingLanguageName).orElse(null);
        //Если сущность присуствует, отправляем запрос на получение количества репозиториев и обновляем значение
        if (programmingLanguage != null) {
            GitHubParser gitHubParser = new GitHubParser();
            Integer count = gitHubParser.getCountRepositories(programmingLanguageName);
            if (count != -1) {
                programmingLanguage.setCountRepositories(count);
                programmingLanguageRepository.save(programmingLanguage);
            } else {
                log.warn("Wrong count repositories");
            }
        } else {
            log.warn(programmingLanguageName + ": does not exist");
        }
    }

    /**
     * Добавление вакансий с hh.ru
     *
     * @param programmingLanguageName название языка программирования
     */
    @Async
    public void addVacancies(ProgrammingLanguageName programmingLanguageName, List<VacancyHHruJson> vacancies) {
        //Получаем сущность языка программирования
        ProgrammingLanguageEntity programmingLanguage = programmingLanguageRepository
                .findByName(programmingLanguageName).orElse(null);
        //Если язык программирования не сохранен, сохраняем вакансии
        if (programmingLanguage != null) {
            for (VacancyHHruJson vacancy : vacancies) {
                saveVacancy(programmingLanguage, vacancy);
            }
        } else {
            log.warn(programmingLanguageName + ": does not exist");
        }
    }

    /**
     * Сохранение вакансии в бд
     *
     * @param programmingLanguage сущность языка программирования
     * @param vacancy             модель вакансии в формате json
     */
    private void saveVacancy(ProgrammingLanguageEntity programmingLanguage,
                             VacancyHHruJson vacancy) {
        VacancyHHruEntity vacancyHHruEntity = vacancyHHruRepository.findByIdHHruAndProgrammingLanguage(vacancy.getId(),
                        programmingLanguage)
                .orElse(null);
        //Если вакансия не найдена в бд, то создаем новую
        if (vacancyHHruEntity == null) {
            SalaryJson salary = vacancy.getSalary();
            SalaryEntity salaryEntity;
            if (salary != null) {
                salaryEntity = new SalaryEntity(salary.getFrom(), salary.getTo(), salary.getCurrency());
            } else {
                salaryEntity = null;
            }
            AreaJson areaJson = vacancy.getArea();
            EmployerJson employerJson = vacancy.getEmployer();
            AreaEntity areaEntity = getAreaEntity(areaJson);
            EmployerEntity employerEntity = getEmployerEntity(employerJson);
            //Получаем дату без часового пояся, в формате yyyy-MM-dd HH:mm:ss
            String date = vacancy.getDatePublish().split("\\+")[0
                    ].replace("T", " ");
            //Создаем новую сущность вакансии
            vacancyHHruEntity = new VacancyHHruEntity(vacancy.getId(), vacancy.getName(),
                    Experience.getExperienceFromString(vacancy.getExperience().id()),
                    Schedule.getScheduleFromString(vacancy.getSchedule().id()),
                    Employment.getEmploymentFromString(vacancy.getEmployment().id()),
                    LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    vacancy.getUrl());
            //Добавляем в вакансию сущности навыков
            for (VacancyParameterName skill : vacancy.getSkills()) {
                vacancyHHruEntity.addSkill(new SkillEntity(skill.name()));
            }
            //Добавляем язык программирования, регион, работодателя, зарплату
            programmingLanguage.addVacancies(vacancyHHruEntity);
            areaEntity.addVacancy(vacancyHHruEntity);
            employerEntity.addVacancies(vacancyHHruEntity);
            vacancyHHruEntity.setSalary(salaryEntity);
            //Сохраняем вакансию
            vacancyHHruRepository.save(vacancyHHruEntity);
            log.info("Вакансия - " + vacancyHHruEntity.getName() + " сохранена");
        } else {
            log.info("Вакансия - " + vacancyHHruEntity.getName() + " уже сохранена в бд");
        }
    }

    /**
     * Получение сущности региона
     *
     * @param areaJson регион в формате json
     * @return сущность региона
     */
    private AreaEntity getAreaEntity(AreaJson areaJson) {
        AreaEntity areaEntity = areaRepository.findByHhRuId(areaJson.getId()).orElse(null);
        if (areaEntity == null) {
            areaRepository.save(new AreaEntity(areaJson.getId(), areaJson.getName()));
            areaEntity = areaRepository.findByHhRuId(areaJson.getId()).get();
            log.info("Регион - " + areaEntity.getName() + " сохранён");
        } else {
            log.info("Регион - " + areaEntity.getName() + "уже сохранён");
        }
        return areaEntity;
    }

    /**
     * Получение сущности работодателя
     *
     * @param employerJson модель работодателя в формате json
     * @return сущность работодателя
     */
    private EmployerEntity getEmployerEntity(EmployerJson employerJson) {
        //Получаем сущность работодателя из бд
        EmployerEntity employerEntity = employerRepository.findByIdHHru(employerJson.getIdHHru()).orElse(null);
        //Если работодателя нету в бд, то создаем сущность и сохраняем в бд
        if (employerEntity == null) {
            String logoUrl;
            EmployerJson.Logo logo = employerJson.getLogo();
            if (logo != null) {
                logoUrl = logo.url();
            } else {
                logoUrl = null;
            }
            employerRepository.save(new EmployerEntity(employerJson.getIdHHru(), employerJson.getName(),
                    employerJson.getUrl(), logoUrl));
            employerEntity = employerRepository.findByIdHHru(employerJson.getIdHHru()).get();
            log.info("Работодатель - " + employerEntity.getName() + " сохранен");
        } else {
            log.info("Работодатель - " + employerEntity.getName() + " уже сохранен");
        }
        return employerEntity;
    }
}
