package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<AreaEntity, Long> {
    Optional<AreaEntity> findByHhRuId(String hhRuId);
    Optional<AreaEntity> findByName(String name);
}
