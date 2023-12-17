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

    @Column(name = "from_salary")
    private Integer from;

    @Column(name = "to_salary")
    private Integer to;

    @Column(name = "currency")
    private String currency;

    public SalaryEntity(Integer from, Integer to, String currency) {
        this.from = from;
        this.to = to;
        this.currency = currency;
    }
}
