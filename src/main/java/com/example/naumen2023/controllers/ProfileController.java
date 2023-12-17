package com.example.naumen2023.controllers;

import com.example.naumen2023.models.entities.TeamEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.services.teams.TeamService;
import com.example.naumen2023.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final TeamService teamService;
    private final UserService userService;
    @Autowired
    public ProfileController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping()
    public String profile(Model model, @AuthenticationPrincipal UserDetails user){
        UserEntity userEntity =  userService.findByUsername(user.getUsername());
        TeamEntity teamEntity = userEntity.getTeam();
        model.addAttribute("user", userEntity);
        model.addAttribute("team", new TeamEntity());
        model.addAttribute("allTeams", teamService.getAll(userEntity.getRequests()));
        return "profile";
    }
}
