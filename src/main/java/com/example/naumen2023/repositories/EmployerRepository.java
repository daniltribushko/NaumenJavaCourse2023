package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.EmployerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<EmployerEntity, Long> {
    Optional<EmployerEntity> findByIdHHru(String idHHru);
    Optional<EmployerEntity> findByName(String name);
}
