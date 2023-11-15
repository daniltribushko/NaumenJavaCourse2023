package com.example.naumen2023.controllers;

import com.example.naumen2023.models.entities.ArticleEntity;
import com.example.naumen2023.models.enums.Statuses;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.UserRepository;
import com.example.naumen2023.services.articles.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final UserRepository userRepository;
    @Autowired
    ArticleController(ArticleService articleService, UserRepository userRepository){
        this.articleService = articleService;
        this.userRepository = userRepository;
    }

    @ResponseBody
    @GetMapping("/articles/{id}")
    public List<ArticleEntity> getAllArticlesById(@PathVariable Long id){
       return articleService.getAllArticlesById(id);
    }

    @GetMapping("/create-article")
    public String createArticle(){
        return "create-article";
    }

    @PostMapping("/create-article")
    public String createArticle(ArticleEntity article, @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userRepository.findByUsername(userDetails.getUsername());
        article.setUser(user);
        article.setStatus(Statuses.UNDER_REVIEW.toString());
        articleService.createArticle(article);
        return "create-article";
    }

    @GetMapping("my-articles")
    public String getArticlesCurrentUser(Model model, @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userRepository.findByUsername(userDetails.getUsername());
        List<ArticleEntity> articles = user.getArticlesList();
        model.addAttribute("articles", articles);
        return "my-articles";
    }
}
