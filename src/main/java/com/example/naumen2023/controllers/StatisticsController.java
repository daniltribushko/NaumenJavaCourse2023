package com.example.naumen2023.controllers;

import com.example.naumen2023.models.dto.response.GetVacanciesResponseDto;
import com.example.naumen2023.models.enums.Employment;
import com.example.naumen2023.models.enums.Experience;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.enums.Schedule;
import com.example.naumen2023.services.vacancies.VacanciesService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Validated
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    private final VacanciesService vacanciesService;

    @Autowired
    public StatisticsController(VacanciesService vacanciesService) {
        this.vacanciesService = vacanciesService;
    }

    @GetMapping("")
    public String viewMainPage(Model model){
        model.addAttribute("progrmmingLanguages", ProgrammingLanguageName.values());
        model.addAttribute("schedule", Schedule.values());
        model.addAttribute("experience", Experience.values());
        model.addAttribute("employment", Employment.values());
        return "statistics";
    }

    @GetMapping("/vacancies")
    public String getVacancies(
            Model model,
            @NotBlank(message = "Programming Language can't be blank") String programmingLanguageName,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String employer,
            @RequestParam(required = false) String experience,
            @RequestParam(required = false) String schedule,
            @RequestParam(required = false) String employment,
            @RequestParam(required = false) Integer fromSalary,
            @RequestParam(required = false) Integer toSalary,
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Page can't be less than 0")
            Integer page,
            @RequestParam(required = false, defaultValue = "20")
            @Min(value = 1, message = "Count can't be less than 1")
            Integer count
    ) {
        model.addAttribute("progrmmingLanguages", ProgrammingLanguageName.values());
        model.addAttribute("schedule", Schedule.values());
        model.addAttribute("experience", Experience.values());
        model.addAttribute("employment", Employment.values());
        model.addAttribute("from", fromSalary);
        model.addAttribute("to", fromSalary);
        model.addAttribute("vacancies", vacanciesService.getVacancies(
                programmingLanguageName,
                area,
                employer,
                experience,
                schedule,
                employment,
                fromSalary,
                toSalary,
                PageRequest.of(page, count))
        );

        return "statistics";
    }

}
