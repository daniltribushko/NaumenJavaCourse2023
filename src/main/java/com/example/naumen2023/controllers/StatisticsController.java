package com.example.naumen2023.controllers;

import com.example.naumen2023.models.dto.request.GetGraphicRequestDto;
import com.example.naumen2023.models.enums.GraphicsSubType;
import com.example.naumen2023.models.enums.GraphicsType;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.services.graphics.GetGraphicsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    @GetMapping("/graphics")
    public String viewGraphics(
            @Valid @RequestBody GetGraphicRequestDto requestDto,
            Model model
    ){
        model.addAttribute("request", new GetGraphicRequestDto());
        model.addAttribute("graphic", GetGraphicsService.getGraphics(requestDto));
        model.addAttribute("programmingLanguages", ProgrammingLanguageName.values());
        model.addAttribute("graphicsType", GraphicsType.values());
        model.addAttribute("graphicsSubType", GraphicsSubType.values());

        return "graphics";
    }
}
