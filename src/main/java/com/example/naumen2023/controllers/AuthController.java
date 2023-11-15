package com.example.naumen2023.controllers;

import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    AuthController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new UserEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(UserEntity user, @RequestParam("confirmPass")
                                        String confirmPass, Model model){
            if(user.getPassword().length()<8){
                model.addAttribute("lengthPassErr", "Пароль должен быть не менее 8 символов!");
                return "registration";
            }

            if(!user.getPassword().equals(confirmPass)){
                model.addAttribute("confirmPassErr", "Пароли должны совпадать!");
                return "registration";
            }
            try{
                userService.addUser(user);
                model.addAttribute("successfullyRegistered", "Вы успешно зарегистрировались!");
                return "registration";
            }
            catch (Exception e){
                model.addAttribute("message", "Пользователь с таким логином уже существует!");
                return "registration";
            }
    }
}
