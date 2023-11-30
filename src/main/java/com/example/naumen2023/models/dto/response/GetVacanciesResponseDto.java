package com.example.naumen2023.models.dto.response;

import com.example.naumen2023.models.entities.SkillEntity;
import com.example.naumen2023.models.entities.VacancyHHruEntity;
import com.example.naumen2023.models.enums.Employment;
import com.example.naumen2023.models.enums.Experience;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.enums.Schedule;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 17.11.2023
 *
 * DTO для ответа на запрос на получение вакансии
 */
@Data
@Builder
public class GetVacanciesResponseDto {
    private String idHHru;
    private String name;
    private ProgrammingLanguageName programmingLanguage;
    private GetAreaResponseDto area;
    private GetSalaryResponseDto salary;
    private Experience experience;
    private Schedule schedule;
    private Employment employment;
    private List<String> skills;
    private GetEmployerResponseDto employer;
    private LocalDateTime datePublish;
    private String url;

    public static GetVacanciesResponseDto mapFromEntity(VacancyHHruEntity vacancy) {
        List<String> skills = vacancy.getSkills()
                .stream()
                .map(SkillEntity::getName)
                .toList();
        return GetVacanciesResponseDto.builder()
                .idHHru(vacancy.getIdHHru())
                .name(vacancy.getName())
                .programmingLanguage(vacancy
                        .getProgrammingLanguage()
                        .getName())
                .area(GetAreaResponseDto.mapFromEntity(vacancy.getArea()))
                .employer(GetEmployerResponseDto.mapFromEntity(vacancy.getEmployer()))
                .salary(GetSalaryResponseDto.mapFromEntity(vacancy.getSalary()))
                .experience(vacancy.getExperience())
                .schedule(vacancy.getSchedule())
                .employment(vacancy.getEmployment())
                .skills(skills)
                .datePublish(vacancy.getDatePublish())
                .url(vacancy.getUrl())
                .build();
    }
}
