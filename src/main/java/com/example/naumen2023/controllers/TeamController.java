package com.example.naumen2023.controllers;


import com.example.naumen2023.models.entities.TeamEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.services.teams.TeamService;
import com.example.naumen2023.services.users.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        userService.addTeam(leader, teamEntity);
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    private String deleteTeam(@AuthenticationPrincipal UserDetails user){
        UserEntity leader =  userService.findByUsername(user.getUsername());
        teamService.delete(leader.getTeam());
        return "redirect:/profile";
    }

    @PostMapping("/sendRequest/{id}")
    public String sendRequest(@PathVariable("id") int idTeam,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam String programmingLanguage){
        TeamEntity teamEntity = teamService.findById(idTeam);
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        user.setProgrammingLanguage(programmingLanguage);
        userService.addTeam(user, teamEntity);
        return "redirect:/profile";
    }

    @PostMapping("/cancel/{id}")
    public String cancelRequest(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable("id") int idTeam){
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        userService.cancelRequest(user, teamService.findById(idTeam));
        return "redirect:/profile";
    }

    

    @PostMapping("/accept/{id}")
    public String acceptInTeam(@PathVariable("id") int idUser, @AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = userService.findById(idUser);
        UserEntity leader =  userService.findByUsername(userDetails.getUsername());
        user.setTeam(leader.getTeam());
        userService.save(user);
        return "redirect:/profile";
    }

    @DeleteMapping("/reject/{id}")
    public String rejectInTeam(@PathVariable("id") int idUser){
        userService.leaveTeam(userService.findById(idUser));
        return "redirect:/profile";
    }

    @PostMapping("/leave")
    public String leaveTeam(@AuthenticationPrincipal UserDetails userDetails){
        UserEntity user =  userService.findByUsername(userDetails.getUsername());
        userService.leaveTeam(user);
        return "redirect:/profile";
    }

}

