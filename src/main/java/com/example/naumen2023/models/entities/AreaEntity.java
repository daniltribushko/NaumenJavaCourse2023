package com.example.naumen2023.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущности региона с hh.ru
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "areas")
public class AreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hh_ru_id", nullable = false)
    private String hhRuId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<VacancyHHruEntity> vacancies = new HashSet<>();

    public AreaEntity(String hhRuId, String name) {
        this.hhRuId = hhRuId;
        this.name = name;
    }

    /**
     * Добавление вакансии в множество
     * @param vacancy сущность вакансии с hh.ru
     */
    public void addVacancy(VacancyHHruEntity vacancy) {
        vacancy.setArea(this);
        vacancies.add(vacancy);
    }
}
