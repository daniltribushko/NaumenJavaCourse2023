package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.ProgrammingLanguageEntity;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguageEntity, Long> {
    Optional<ProgrammingLanguageEntity> findByName(ProgrammingLanguageName programmingLanguageName);
}
