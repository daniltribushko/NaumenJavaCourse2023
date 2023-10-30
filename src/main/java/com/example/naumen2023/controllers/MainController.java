package com.example.naumen2023.controllers;

import com.example.naumen2023.models.entities.VacancyHHruEntity;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.repositories.VacancyHHruRepository;
import com.example.naumen2023.services.programminglanguage.ProgrammingLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    private ProgrammingLanguageService service;
    private VacancyHHruRepository vacancyHHruRepository;
    @Autowired
    public MainController(ProgrammingLanguageService service, VacancyHHruRepository vacancyHHruRepository){
        this.service = service;
        this.vacancyHHruRepository = vacancyHHruRepository;
    }

    @GetMapping("/")
    @ResponseBody
    public List<VacancyHHruEntity> a(){
        vacancyHHruRepository.findAll().forEach(v -> System.out.println(v.getSalary()));
        return vacancyHHruRepository.findAll();
    }
}
