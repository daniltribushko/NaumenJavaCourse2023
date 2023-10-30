package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.ProgrammingLanguageEntity;
import com.example.naumen2023.models.entities.VacancyHHruEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacancyHHruRepository extends JpaRepository<VacancyHHruEntity, Long> {
    Optional<VacancyHHruEntity> findByIdHHruAndProgrammingLanguage(String idHHru,
                                                                   ProgrammingLanguageEntity programmingLanguage);
}
