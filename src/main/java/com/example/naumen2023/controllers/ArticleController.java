package com.example.naumen2023.controllers;

import com.example.naumen2023.models.entities.ArticleEntity;
import com.example.naumen2023.models.enums.Statuses;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.UserRepository;
import com.example.naumen2023.services.articles.ArticleService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/article")
    public String getMyArticle(Model model, @RequestParam long idArticle){
        ArticleEntity article = articleService.getArticleById(idArticle);
        model.addAttribute("article", article);
        return "my-article";
    }

    @GetMapping("/articles")
    public String getAllArticles(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                 Model model, @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userRepository.findByUsername(userDetails.getUsername());
        Page<ArticleEntity> articlesPage = articleService.getAllArticles(page, 1, user);
        int totalPages = articlesPage.getTotalPages();
        int maxPagesToShow = 3;
        int startPage;
        int endPage;
        if(totalPages <= maxPagesToShow) {
            startPage = 0;
            endPage = totalPages-1;
        }
        else {
            int halfPagesToShow = maxPagesToShow / 2;
            startPage = Math.max(0, page - halfPagesToShow);
            endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);

            if (endPage - startPage < maxPagesToShow - 1) {
                startPage = Math.max(0, endPage - maxPagesToShow + 1);
            }
        }
        model.addAttribute("articles", articlesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlesPage.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "articles";
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
