package com.example.naumen2023.services.vacancies;

import com.example.naumen2023.models.dto.response.GetVacanciesResponseDto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface VacanciesService {
    List<GetVacanciesResponseDto> getVacancies(
            @NotBlank(message = "Programming Language can't be blank") String programmingLanguageName,
            String area,
            String employer,
            String experience,
            String schedule,
            String employment,
            Integer fromSalary,
            Integer toSalary,
            Pageable pageable);

}
