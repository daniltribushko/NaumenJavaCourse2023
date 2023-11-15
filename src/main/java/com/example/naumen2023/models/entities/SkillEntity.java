package com.example.naumen2023.models.entities;

import com.example.naumen2023.models.entities.VacancyHHruEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Сущности навыка
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "skills")
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private VacancyHHruEntity vacancy;

    public SkillEntity(String name){
        this.name = name;
    }
}
