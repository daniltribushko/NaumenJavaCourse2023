package com.example.naumen2023.models.gson;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс вакансии для преобразования json обьекта
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
@ToString
@AllArgsConstructor
public class VacancyHHruJson {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("area")
    private AreaJson area;

    @SerializedName("salary")
    private SalaryJson salary;

    @SerializedName("experience")
    private VacancyParameterId experience;

    @SerializedName("schedule")
    private VacancyParameterId schedule;

    @SerializedName("employment")
    private VacancyParameterId employment;

    @SerializedName("skills")
    private VacancyParameterName[] skills;

    @SerializedName("employer")
    private EmployerJson employer;

    @SerializedName("date_publish")
    private String datePublish;

    @SerializedName("url")
    private String url;
}
