package com.example.naumen2023.services.articles;

import com.example.naumen2023.models.entities.ArticleEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.ArticleRepository;
import com.example.naumen2023.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Autowired
    ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public List<ArticleEntity> getAllArticlesByUser(UserEntity userEntity){
        return articleRepository.findArticleEntityByUser(userEntity);
    }

//    public String formatBody(String body){
//        String[] bodySplit = body.split("\\R");
//        List<String> nonEmptyLines = Arrays.stream(bodySplit)
//                .filter(line -> !line.trim().isEmpty())
//                .collect(Collectors.toList());
//        String result = "<p>" + String.join("</p><p>", nonEmptyLines) + "</p>";
//        result = result.replaceAll("</p>", "</p>\n\n");
//        result = result.replaceFirst("(?s)</p>\\s*$", "</p>");
//        return result;
//    }
    @Transactional
    public void createArticle(ArticleEntity newArticle){
        articleRepository.save(newArticle);
    }
    @Transactional
    public void deleteArticle(Long id){
        articleRepository.delete(articleRepository.findArticleEntityById(id));
    }
    public ArticleEntity getArticleById(Long id){
       return articleRepository.findArticleEntityById(id);
    }
}
