package com.example.naumen2023.models.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность зарплаты
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "salaries")
public class SalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from")
    private Integer from;

    @Column(name = "to")
    private Integer to;

    @Column(name = "currency")
    private String currency;

    @OneToOne
    @JoinColumn(name = "vacancy_id", nullable = false)
    private VacancyHHruEntity vacancy;

    public SalaryEntity(Integer from, Integer to, String currency) {
        this.from = from;
        this.to = to;
        this.currency = currency;
    }
}
