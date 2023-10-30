package com.example.naumen2023.services.programminglanguage;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.gson.VacancyHHruJson;

import java.util.List;

public interface ProgrammingLanguageServiceInterface {
    void updateCountRepositories(ProgrammingLanguageName programmingLanguageName);
    void addVacancies(ProgrammingLanguageName programmingLanguageName, List<VacancyHHruJson> vacancies);
}
