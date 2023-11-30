package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    TeamEntity findByName(String name);
}
