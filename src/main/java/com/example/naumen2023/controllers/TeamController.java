package com.example.naumen2023.controllers;


import com.example.naumen2023.models.entities.TeamEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.services.teams.TeamService;
import com.example.naumen2023.services.users.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
        teamService.createTeam(leader, teamEntity);
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    private String deleteTeam(@AuthenticationPrincipal UserDetails user){
        UserEntity leader =  userService.findByUsername(user.getUsername());
        teamService.deleteTeam(leader.getTeam());
        return "redirect:/profile";
    }

    @PostMapping("/sendRequest/{id}")
    public String sendRequest(@PathVariable("id") int idTeam,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam String programmingLanguage){
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        teamService.addRequest(user, programmingLanguage, teamService.findById(idTeam));
        return "redirect:/profile";
    }

    @PostMapping("/cancel/{id}")
    public String cancelRequest(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable("id") int idTeam){
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        teamService.deleteRequest(user, teamService.findById(idTeam));
        return "redirect:/profile";
    }

    

    @PostMapping("/accept/{username}")
    public String acceptInTeam(@PathVariable("username") String username, @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userService.findByUsername(username);
        UserEntity leader =  userService.findByUsername(userDetails.getUsername());
        teamService.acceptUser(user, leader.getTeam());
        return "redirect:/profile";
    }

    @DeleteMapping("/reject/{username}")
    public String rejectInTeam(@PathVariable("username") String username,  @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userService.findByUsername(username);
        UserEntity leader =  userService.findByUsername(userDetails.getUsername());
        teamService.rejectUser(user, leader.getTeam());
        return "redirect:/profile";
    }

    @PostMapping("/leave")
    public String leaveTeam(@AuthenticationPrincipal UserDetails userDetails){
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        teamService.leaveTeam(user, user.getTeam());
        return "redirect:/profile";
    }

    @PostMapping("/leave/{username}")
    public String leaveTeam(@PathVariable("username") String username){
        UserEntity user = userService.findByUsername(username);
        teamService.leaveTeam(user, user.getTeam());
        return "redirect:/profile";
    }

}

