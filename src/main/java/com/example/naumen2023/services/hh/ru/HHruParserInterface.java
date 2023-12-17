package com.example.naumen2023.services.hh.ru;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.gson.VacancyHHruJson;

import java.util.List;

public interface HHruParserInterface {
    List<VacancyHHruJson> getAllVacanciesFromRussia(ProgrammingLanguageName programmingLanguage);
}
