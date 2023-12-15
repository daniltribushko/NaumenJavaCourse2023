package com.example.naumen2023.repositories;

import com.example.naumen2023.models.entities.ArticleEntity;
import com.example.naumen2023.models.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    List<ArticleEntity> findArticleEntityByUser(UserEntity user);
    ArticleEntity findArticleEntityById(Long id);
    Page<ArticleEntity> findByUserNot(UserEntity user, Pageable pageable);
}
