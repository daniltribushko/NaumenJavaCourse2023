package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.EmployerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<EmployerEntity, Long> {
}
