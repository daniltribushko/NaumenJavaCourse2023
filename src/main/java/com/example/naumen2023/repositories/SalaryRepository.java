package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {
}
