package com.example.naumen2023.models.enums;

import lombok.Getter;

/**
 * Класс содержащий константы для проекта
 */
@Getter
public enum GraphicsTitleAndFileName {
    TOP_10_AREAS_BY_COUNT_VACANCIES("Топ 10 городов по количеству вакансий",
            "top_10_areas_by_count_vacancies"),
    TOP_10_AREAS_BY_MIDDLE_SALARY("Топ 10 городов по средней зарплате",
            "top_10_areas_by_middle_salary"),
    DISTRIBUTION_OF_VACANCIES_FROM_AREAS("Распределение вакансий среди городов",
            "distribution_of_vacancies_from_areas"),
    TOP_10_EMPLOYERS_BY_COUNT_VACANCIES("Топ 10 работодателей по количеству вакансий",
            "top_10_employers_by_count_vacancies"),
    TOP_10_EMPLOYERS_BY_MIDDLE_SALARY("Топ 10 работодателей по средней зарплате",
            "top_10_employers_by_middle_salary"),
    DISTRIBUTION_OF_VACANCIES_FROM_EMPLOYERS("Распределение вакансий среди работодателей",
            "distribution_of_vacancies_from_employers");
    private String title;
    private String fileName;

    GraphicsTitleAndFileName(String title, String fileName){
        this.title = title;
        this.fileName = fileName;
    }

}
