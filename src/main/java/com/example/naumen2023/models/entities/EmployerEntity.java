package com.example.naumen2023.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Сущность работодателя
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "employers")
public class EmployerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hh_ru_id", nullable = false)
    private String idHHru;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private Set<VacancyHHruEntity> vacancies = new HashSet<>();

    public EmployerEntity(String idHHru, String name, String url, String logo) {
        this.idHHru = idHHru;
        this.name = name;
        this.url = url;
        this.logo = logo;
    }

    public void addVacancies(VacancyHHruEntity vacancy){
        vacancy.setEmployer(this);
        vacancies.add(vacancy);
    }
}
