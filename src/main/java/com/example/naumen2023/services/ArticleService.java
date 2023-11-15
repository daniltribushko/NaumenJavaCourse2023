package com.example.naumen2023.services;

import com.example.naumen2023.models.entities.ArticleEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.ArticleRepository;
import com.example.naumen2023.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    ArticleService(ArticleRepository articleRepository, UserRepository userRepository){
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<ArticleEntity> getAllArticlesById(Long id){
        UserEntity user = userRepository.findByIdUser(id);
        return user.getArticlesList();
    }

    public void createArticle(ArticleEntity newArticle){
        articleRepository.save(newArticle);
    }
}
