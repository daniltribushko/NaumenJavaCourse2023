package com.example.naumen2023.models.entities;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Сущность языка программирования
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "programming_language")
public class ProgrammingLanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private ProgrammingLanguageName name;

    @Column(name = "count_repositories", nullable = false)
    private Integer countRepositories;

    @OneToMany(mappedBy = "programmingLanguage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<VacancyHHruEntity> vacancies = new HashSet<>();

    public ProgrammingLanguageEntity(ProgrammingLanguageName name) {
        this.name = name;
        this.countRepositories = 0;
    }

    public void addVacancies(VacancyHHruEntity vacancy) {
        vacancy.setProgrammingLanguage(this);
        vacancies.add(vacancy);
    }
}
