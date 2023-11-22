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
import java.util.Map;
import java.util.Set;

/**
 * Сервис для работы с сущностями языков программирования
 *
 * @author Tribushko Danil
 * @since 30.10.2023
 */
@Slf4j
@Service
public class ProgrammingLanguageService {
    private ProgrammingLanguageRepository programmingLanguageRepository;
    private VacancyHHruRepository vacancyHHruRepository;
    private AreaRepository areaRepository;
    private EmployerRepository employerRepository;
    private String[] programmingLanguageName;

    @Autowired
    public ProgrammingLanguageService(ProgrammingLanguageRepository programmingLanguageRepository,
                                      VacancyHHruRepository vacancyHHruRepository, AreaRepository areaRepository,
                                      EmployerRepository employerRepository) {
        this.programmingLanguageRepository = programmingLanguageRepository;
        this.vacancyHHruRepository = vacancyHHruRepository;
        this.areaRepository = areaRepository;
        this.employerRepository = employerRepository;
        programmingLanguageName = new String[]{
                "java",
                "go",
                "golang",
                "kotlin",
                "scala",
                "c#",
                "c++",
                "lua",
                "rust",
                "ruby",
                "php",
                "python",
                "javascript",
                "typescript"
        };
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

    private void addSkills(VacancyHHruEntity vacancy, VacancyParameterName[] skills) {
        boolean isProgrammingLanguage;
        for (VacancyParameterName skill : skills) {
            isProgrammingLanguage = false;
            String skillName = skill.name();
            for (String pl : programmingLanguageName) {
                if (skillName.toLowerCase().equals(pl)) {
                    isProgrammingLanguage = true;
                    break;
                }
            }
            if (!isProgrammingLanguage) {
                vacancy.addSkill(new SkillEntity(skillName));
            }
        }
    }

    @Async
    protected void saveAreas(Set<AreaJson> areas) {
        for (AreaJson area : areas) {
            try {
                AreaEntity areaEntity = areaRepository.
                        findByHhRuId(area.getId())
                        .orElse(null);
                if (areaEntity == null) {
                    areaRepository.save(new AreaEntity(area.getId(), area.getName()));
                    log.info("area saved");
                } else {
                    log.info("area already save");
                }
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
    }

    @Async
    protected void saveEmployers(Set<EmployerJson> employers) {
        for (EmployerJson employer : employers) {
            EmployerEntity employerEntity = employerRepository
                    .findByIdHHru(employer.getIdHHru())
                    .orElse(null);;
            if (employerEntity == null) {
                EmployerJson.Logo logo = employer.getLogo();
                String logoUrl = null;
                if (logo != null) {
                    logoUrl = logo.url();
                }
                employerRepository.save(new EmployerEntity(
                        employer.getIdHHru(),
                        employer.getName(),
                        employer.getUrl(),
                        logoUrl)
                );
                log.info("employer saved");
            } else {
                log.info("employer already saved");
            }
        }
    }

    public void saveAreasAndEmployers(Set<AreaJson> areas, Set<EmployerJson> employers){
        saveAreas(areas);
        saveEmployers(employers);
    }
    @Async
    public void saveVacancies(ProgrammingLanguageName programmingLanguageName, List<VacancyHHruJson> vacancies) {
        ProgrammingLanguageEntity programmingLanguage = programmingLanguageRepository
                .findByName(programmingLanguageName)
                .orElse(null);
        if (programmingLanguage != null) {
            for (VacancyHHruJson vacancy : vacancies) {
                try {
                    VacancyHHruEntity vacancyHHruEntity = vacancyHHruRepository
                            .findByIdHHruAndProgrammingLanguage(vacancy.getId(), programmingLanguage)
                            .orElse(null);
                    if (vacancyHHruEntity == null) {
                        EmployerEntity employer = employerRepository
                                .findByIdHHru(vacancy.getEmployer().getIdHHru())
                                .orElse(null);
                        AreaEntity area = areaRepository
                                .findByHhRuId(vacancy.getArea().getId())
                                .orElse(null);
                        System.out.println(employer);
                        System.out.println(area);
                        if (employer != null && area != null) {
                            SalaryEntity salaryEntity = getSalaryEntity(vacancy);
                            String date = vacancy.getDatePublish().split("\\+")[0
                                    ].replace("T", " ");
                            //Создаем новую сущность вакансии
                            vacancyHHruEntity = new VacancyHHruEntity(
                                    vacancy.getId(),
                                    vacancy.getName(),
                                    Experience.getExperienceFromString(
                                            vacancy
                                                    .getExperience()
                                                    .id()
                                    ),
                                    Schedule.getScheduleFromString(
                                            vacancy
                                                    .getSchedule()
                                                    .id()
                                    ),
                                    Employment.getEmploymentFromString(
                                            vacancy
                                                    .getEmployment()
                                                    .id()
                                    ),
                                    LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                    vacancy.getUrl());
                            //Добавляем в вакансию сущности навыков
                            addSkills(vacancyHHruEntity, vacancy.getSkills());
                            //Добавляем язык программирования, регион, работодателя, зарплату
                            programmingLanguage.addVacancies(vacancyHHruEntity);
                            area.addVacancy(vacancyHHruEntity);
                            employer.addVacancies(vacancyHHruEntity);
                            vacancyHHruEntity.setSalary(salaryEntity);
                            //Сохраняем вакансию
                            vacancyHHruRepository.save(vacancyHHruEntity);
                            log.info("vacancy saved");
                        }
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }

    private static SalaryEntity getSalaryEntity(VacancyHHruJson vacancy) {
        SalaryJson salary = vacancy.getSalary();
        SalaryEntity salaryEntity;
        if (salary != null) {
            salaryEntity = new SalaryEntity(
                    salary.getFrom(),
                    salary.getTo(),
                    salary.getCurrency()
            );
        } else {
            salaryEntity = null;
        }
        return salaryEntity;
    }
}
