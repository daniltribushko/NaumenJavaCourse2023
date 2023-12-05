package com.example.naumen2023.controllers;


import com.example.naumen2023.models.entities.TeamEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.services.teams.TeamService;
import com.example.naumen2023.services.users.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final UserService userService;

    @Autowired
    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }
    @PostMapping("/create")
    public String createTeam(@ModelAttribute("team") @Valid TeamEntity teamEntity,
                             @AuthenticationPrincipal UserDetails user){
        UserEntity leader =  userService.findByUsername(user.getUsername());
        teamService.save(teamEntity);
        userService.addTeam(leader, teamEntity, "лидер");
        return "redirect:/profile";
    }

    @PostMapping("/addUser/{id}")
    public String addUser(@PathVariable("id") int idTeam, @AuthenticationPrincipal UserDetails userDetails){
        TeamEntity teamEntity = teamService.findById(idTeam);
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        userService.addTeam(user, teamEntity, "участник");
        return "redirect:/profile";
    }

    @PostMapping("/leave")
    public String leaveTeam(@AuthenticationPrincipal UserDetails userDetails){
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        userService.leaveTeam(user);
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    private String deleteTeam(@AuthenticationPrincipal UserDetails user){
        UserEntity leader =  userService.findByUsername(user.getUsername());
        teamService.delete(leader.getTeam());
        return "redirect:/profile";
    }

}

