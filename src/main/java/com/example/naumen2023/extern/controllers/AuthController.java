package com.example.naumen2023.extern.controllers;

import com.example.naumen2023.extern.entities.UserEntity;
import com.example.naumen2023.extern.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new UserEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid UserEntity user, Model model, BindingResult result){
        if (result.hasErrors()) {
            return "registration";
        }
        else{
            try{
                userService.addUser(user);
                return "redirect:/login";
            }
            catch (Exception e){
                model.addAttribute("message", "Пользователь с таким логином уже существует!");
                return "registration";
            }
        }
    }

    @ResponseBody
    @GetMapping("/users")
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }
}
