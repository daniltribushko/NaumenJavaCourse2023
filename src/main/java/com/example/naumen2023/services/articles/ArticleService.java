package com.example.naumen2023.services.articles;

import com.example.naumen2023.models.entities.ArticleEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


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

    public Page<ArticleEntity> getAllArticles(Integer page, Integer size, String status, UserEntity user){
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findByUserNotAndStatus(user, status, pageable);
    }

    public List<ArticleEntity> getArticlesByStatus(String status){
        return articleRepository.findArticleEntityByStatus(status);
    }

    public boolean editArticleStatus(String status, long idArticle){
        ArticleEntity article = articleRepository.findArticleEntityById(idArticle);
        article.setStatus(status);
        articleRepository.save(article);
        return true;
    }
}
