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

    @GetMapping("/create-article")
    public String createArticle(){
        return "create-article";
    }

    @PostMapping("/create-article")
    public String createArticle(ArticleEntity article, @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userRepository.findByUsername(userDetails.getUsername());
            article.setUser(user);
            article.setStatus(Statuses.UNDER_REVIEW.toString());
            System.out.println(article.getBody());
            articleService.createArticle(article);
        return "create-article";
    }

    @PostMapping("/update-article")
    public String updateArticle(ArticleEntity article){
        ArticleEntity articleEntity = articleService.getArticleById(article.getId());
        articleEntity.setTitle(article.getTitle());
        articleEntity.setBody(article.getBody());
        articleService.createArticle(articleEntity);
        return "redirect:/my-articles";
    }

    @GetMapping("/my-articles")
    public String getArticlesCurrentUser(Model model, @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userRepository.findByUsername(userDetails.getUsername());
        List<ArticleEntity> articles = articleService.getAllArticlesByUser(user);
        model.addAttribute("articles", articles);
        return "my-articles";
    }

    @GetMapping("/my-article")
    public String getMyArticle(Model model, @RequestParam long idArticle){
        ArticleEntity article = articleService.getArticleById(idArticle);
        model.addAttribute("article", article);
        return "my-article";
    }

    @GetMapping("/update")
    public String updateArticle(Model model, @RequestParam long idArticle){
        ArticleEntity article = articleService.getArticleById(idArticle);
        model.addAttribute("title", article.getTitle());
        model.addAttribute("body", article.getBody());
        model.addAttribute("idArticle", article.getId());
        return "update-article";
    }

    @PostMapping("/delete")
    public String deleteArticle(@RequestParam long idArticle){
        articleService.deleteArticle(idArticle);
        return "redirect:/my-articles";
    }
}
