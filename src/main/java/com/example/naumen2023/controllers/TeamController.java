package com.example.naumen2023.controllers;


import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class TeamController {
    private final UserRepository repository;
    @Autowired
    public TeamController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public String profile(Model model, @AuthenticationPrincipal UserDetails user){
        UserEntity userEntity =  repository.findByUsername(user.getUsername());
        model.addAttribute("user", userEntity);
        return "profile";
    }
}

