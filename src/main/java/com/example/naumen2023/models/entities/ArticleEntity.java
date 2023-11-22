package com.example.naumen2023.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "article_entity")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String title;
    private String status;
    @Column(columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = UserEntity.class)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;
}
