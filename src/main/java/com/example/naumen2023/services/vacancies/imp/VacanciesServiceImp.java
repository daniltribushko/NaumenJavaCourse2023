package com.example.naumen2023.services.vacancies.imp;

import com.example.naumen2023.exeptions.*;
import com.example.naumen2023.models.dto.response.GetAreaResponseDto;
import com.example.naumen2023.models.dto.response.GetEmployerResponseDto;
import com.example.naumen2023.models.dto.response.GetVacanciesResponseDto;
import com.example.naumen2023.models.entities.AreaEntity;
import com.example.naumen2023.models.entities.EmployerEntity;
import com.example.naumen2023.models.entities.VacancyHHruEntity;
import com.example.naumen2023.models.enums.Employment;
import com.example.naumen2023.models.enums.Experience;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.enums.Schedule;
import com.example.naumen2023.repositories.AreaRepository;
import com.example.naumen2023.repositories.EmployerRepository;
import com.example.naumen2023.repositories.VacancyHHruPageRepository;
import com.example.naumen2023.services.vacancies.VacanciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacanciesServiceImp implements VacanciesService {
    private final VacancyHHruPageRepository vacancyHHruPageRepository;
    private final AreaRepository areaRepository;
    private final EmployerRepository employerRepository;

    @Autowired
    public VacanciesServiceImp(
            VacancyHHruPageRepository vacancyHHruPageRepository,
            AreaRepository areaRepository, EmployerRepository employerRepository) {
        this.vacancyHHruPageRepository = vacancyHHruPageRepository;
        this.areaRepository = areaRepository;
        this.employerRepository = employerRepository;
    }

    /**
     * Получение вакансий с hh.ru из бд
     *
     * @param programmingLanguageName язык программирования
     * @param area                    регион
     * @param employer                работодатель
     * @param experience              опыт работы
     * @param schedule                график работы
     * @param employment              тип занятости
     * @param fromSalary              зарплата от
     * @param toSalary                зарплата до
     * @param pageable                параметры страницы запроса
     * @return список вакансий с указанными параметрами
     */
    @Override
    public List<GetVacanciesResponseDto> getVacancies(
            String programmingLanguageName,
            String area,
            String employer,
            String experience,
            String schedule,
            String employment,
            Integer fromSalary,
            Integer toSalary,
            Pageable pageable) {
        List<VacancyHHruEntity> vacancies;
        ProgrammingLanguageName programmingLanguage = ProgrammingLanguageName
                .mapFromString(programmingLanguageName);
        if (programmingLanguage != null) {
            vacancies = vacancyHHruPageRepository.findAllByProgrammingLanguage_Name(programmingLanguage, pageable);
        } else {
            throw new ProgrammingLanguageNotFoundException(programmingLanguageName);
        }
        if (area != null) {
            AreaEntity areaEntity = areaRepository.findByName(area)
                    .orElseThrow(() -> new AreaNotFoundException(area));
            vacancies = vacancies.stream()
                    .filter(v -> v.getArea().equals(areaEntity))
                    .toList();
        }
        if (employer != null) {
            EmployerEntity employerEntity = employerRepository.findByName(employer)
                    .orElseThrow(() -> new EmployerNotFoundException(employer));
            vacancies = vacancies.stream()
                    .filter(v -> v.getEmployer().equals(employerEntity))
                    .toList();
        }
        if (experience != null) {
            vacancies = getVacanciesByExperience(vacancies, experience);
        }
        if (employment != null) {
            vacancies = getVacanciesByEmployment(vacancies, employment);
        }
        if (schedule != null) {
            vacancies = getVacanciesBySchedule(vacancies, schedule);
        }
        if (fromSalary != null) {
            vacancies = getVacanciesByFromSalary(vacancies, fromSalary);
        }
        if (toSalary != null) {
            vacancies = getVacanciesByToSalary(vacancies, toSalary);
        }

        return vacancies.stream()
                .map(GetVacanciesResponseDto::mapFromEntity)
                .toList();
    }

    @Override
    public List<GetAreaResponseDto> getAllAreas() {
        return areaRepository.findAll().stream().map(GetAreaResponseDto::mapFromEntity).toList();
    }

    @Override
    public List<GetEmployerResponseDto> getAllEmployers() {
        return employerRepository.findAll().stream().map(GetEmployerResponseDto::mapFromEntity).toList();
    }

    /**
     * Получение списка вакансий по опыту работы
     *
     * @param vacancies  список вакансий
     * @param experience опыт работы
     * @return список вакансий
     */
    private List<VacancyHHruEntity> getVacanciesByExperience(List<VacancyHHruEntity> vacancies, String experience) {
        List<VacancyHHruEntity> result;
        Experience vacancyExperience = Experience.getExperienceFromString(experience);
        if (vacancyExperience != null) {
            result = vacancies.stream()
                    .filter(v -> v.getExperience().equals(vacancyExperience))
                    .toList();
            if (result.isEmpty()) {
                result = vacancies;
            }
        } else {
            throw new WrongExperienceException();
        }

        return result;
    }

    /**
     * Получение вакансий по типу занятости
     *
     * @param vacancies  список вакансий
     * @param employment тип занятости
     * @return список вакансий
     */
    private List<VacancyHHruEntity> getVacanciesByEmployment(List<VacancyHHruEntity> vacancies, String employment) {
        List<VacancyHHruEntity> result;
        Employment vacancyEmployment = Employment.getEmploymentFromString(employment);
        if (vacancyEmployment != null) {
            result = vacancies.stream()
                    .filter(v -> v.getEmployment().equals(vacancyEmployment))
                    .toList();
            if (result.isEmpty()) {
                result = vacancies;
            }
        } else {
            throw new WrongEmploymentException();
        }
        return result;
    }

    /**
     * Получение вакансий по графику работы
     *
     * @param vacancies список вакансий
     * @param schedule  график работы
     * @return список вакансий
     */
    private List<VacancyHHruEntity> getVacanciesBySchedule(List<VacancyHHruEntity> vacancies, String schedule) {
        List<VacancyHHruEntity> result;
        Schedule vacancySchedule = Schedule.getScheduleFromString(schedule);
        if (vacancySchedule != null) {
            result = vacancies.stream()
                    .filter(v -> v.getSchedule().equals(vacancySchedule))
                    .toList();
            if (result.isEmpty()) {
                result = vacancies;
            }
        } else {
            throw new WrongScheduleException();
        }
        return result;
    }

    /**
     * Получение вакансий по левой границе зарплаты
     *
     * @param vacancies список вакансий
     * @param from      левая граница зарплаты
     * @return список вакансий
     */
    private List<VacancyHHruEntity> getVacanciesByFromSalary(List<VacancyHHruEntity> vacancies, Integer from) {
        List<VacancyHHruEntity> result = vacancies.stream()
                .filter(v -> v.getSalary() != null && v.getSalary().getFrom() >= from)
                .toList();
        if (result.isEmpty()) {
            result = vacancies;
        }
        return result;
    }

    /**
     * Получение вакансий по правой границе зарплаты
     *
     * @param vacancies список вакансий
     * @param to        правая граница зарплаты
     * @return список вакансий
     */
    private List<VacancyHHruEntity> getVacanciesByToSalary(List<VacancyHHruEntity> vacancies, Integer to) {
        List<VacancyHHruEntity> result = vacancies.stream()
                .filter(v -> v.getSalary() != null && v.getSalary().getTo() <= to)
                .toList();
        if (result.isEmpty()) {
            result = vacancies;
        }
        return result;
    }
}
