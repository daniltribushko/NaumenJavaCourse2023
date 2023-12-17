package com.example.naumen2023.controllers;

import com.example.naumen2023.models.entities.ArticleEntity;
import com.example.naumen2023.models.enums.Statuses;
import com.example.naumen2023.services.articles.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {
    public final ArticleService articleService;

    @Autowired
    public ModeratorController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getNotVerifiedArticles(Model model){
        List<ArticleEntity> notVerifiedArticles = articleService.getArticlesByStatus(Statuses.UNDER_REVIEW.toString());
        model.addAttribute("articles", notVerifiedArticles);
        return "verify";
    }

    @PostMapping("/allow")
    public String allowPublication(@RequestParam long idArticle){
        articleService.editArticleStatus(Statuses.PUBLISHED.toString(), idArticle);
        return "redirect:/moderator/articles";
    }
    @PostMapping("/reject")
    public String rejectPublication(@RequestParam long idArticle){
        articleService.editArticleStatus(Statuses.REJECTED.toString(), idArticle);
        return "redirect:/moderator/articles";
    }

}
