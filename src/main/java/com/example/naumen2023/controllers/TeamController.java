package com.example.naumen2023.controllers;


import com.example.naumen2023.models.entities.TeamEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private final UserRepository repository;
    @Autowired
    public TeamController(UserRepository repository) {
        this.repository = repository;
    }
    @PostMapping("/create")
    public String createTeam(@ModelAttribute("team") @Valid TeamEntity teamEntity, BindingResult bindingResult){
        System.out.println(".");
        for (ProgrammingLanguageName language : teamEntity.getLanguages()){
            System.out.println(language);
        }

        return "/";
    }
}

