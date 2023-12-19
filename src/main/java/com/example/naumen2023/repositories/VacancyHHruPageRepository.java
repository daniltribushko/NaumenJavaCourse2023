package com.example.naumen2023.repositories;


import com.example.naumen2023.models.entities.VacancyHHruEntity;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VacancyHHruPageRepository extends PagingAndSortingRepository<VacancyHHruEntity, Long> {
    List<VacancyHHruEntity> findAllByProgrammingLanguage_Name(ProgrammingLanguageName programmingLanguageName,
                                                              Pageable pageable);
    Page<VacancyHHruEntity> findAll(Pageable pageable);
}
