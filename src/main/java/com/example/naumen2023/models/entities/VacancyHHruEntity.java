package com.example.naumen2023.models.entities;

import com.example.naumen2023.models.enums.Employment;
import com.example.naumen2023.models.enums.Experience;
import com.example.naumen2023.models.enums.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущности вакансии с hh.ru
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "vacancies_hh_ru")
public class VacancyHHruEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hh_ru_id", nullable = false)
    private String idHHru;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "programmingLanguage_id")
    private ProgrammingLanguageEntity programmingLanguage;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "salary_id")
    private SalaryEntity salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience", nullable = false)
    private Experience experience;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule", nullable = false)
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment", nullable = false)
    private Employment employment;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SkillEntity> skills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;

    @Column(name = "date_publish", nullable = false)
    private LocalDateTime datePublish;

    @Column(name = "url", nullable = false)
    private String url;

    public VacancyHHruEntity(String idHHru, String name,
                             Experience experience, Schedule schedule, Employment employment,
                             LocalDateTime datePublish, String url) {
        this.idHHru = idHHru;
        this.name = name;
        this.experience = experience;
        this.schedule = schedule;
        this.employment = employment;
        this.datePublish = datePublish;
        this.url = url;
    }

    public void addSkill(SkillEntity skill){
        skill.setVacancy(this);
        skills.add(skill);
    }
}
