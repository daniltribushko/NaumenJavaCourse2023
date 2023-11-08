package com.example.naumen2023.services;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.gson.VacancyHHruJson;
import com.example.naumen2023.services.graphics.GraphicsDrawer;
import com.example.naumen2023.services.hh.ru.HHruParser;
import com.example.naumen2023.services.programminglanguage.ProgrammingLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdaterScheduleService {
    private final ProgrammingLanguageService programmingLanguageService;
    private final GraphicsDrawer graphicsDrawer;

    @Autowired

    public UpdaterScheduleService(ProgrammingLanguageService programmingLanguageService,
                                  GraphicsDrawer graphicsDrawer){
        this.programmingLanguageService = programmingLanguageService;
        this.graphicsDrawer = graphicsDrawer;
    }

    @Async
    @Scheduled(cron = "0 52 20 * * *")
    public void saveAllVacancies() {
        HHruParser parser = new HHruParser();
        Map<ProgrammingLanguageName, List<VacancyHHruJson>> vacancies = new HashMap<>();
        for (ProgrammingLanguageName programmingLanguageName : ProgrammingLanguageName.values()){
            List<VacancyHHruJson> vacanciesJson = parser.getAllVacanciesFromRussia(programmingLanguageName);
            vacancies.put(programmingLanguageName, vacanciesJson);
        }
        for (Map.Entry<ProgrammingLanguageName, List<VacancyHHruJson>> vacancy : vacancies.entrySet()){
            programmingLanguageService.addVacancies(vacancy.getKey(), vacancy.getValue());
        }
    }
    @Async
    @Scheduled(cron = "0 11 22 * * *")
    public void updateCountRepositories() throws MalformedURLException, InterruptedException {
        for (ProgrammingLanguageName programmingLanguageName : ProgrammingLanguageName.values()){
            programmingLanguageService.updateCountRepositories(programmingLanguageName);
        }
    }

    @Async
    @Scheduled(cron = "0 56 19 * * *")
    public void drawGraphics(){
        graphicsDrawer.drawAllGraphics();
    }
}
