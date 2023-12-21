package com.example.naumen2023.services;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.gson.AreaJson;
import com.example.naumen2023.models.gson.EmployerJson;
import com.example.naumen2023.models.gson.VacancyHHruJson;
import com.example.naumen2023.services.graphics.GraphicsDrawer;
import com.example.naumen2023.services.hh.ru.HHruParser;
import com.example.naumen2023.services.programminglanguage.ProgrammingLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.*;

@Service
public class UpdaterScheduleService {
    private final ProgrammingLanguageService programmingLanguageService;
    private final GraphicsDrawer graphicsDrawer;

    @Autowired

    public UpdaterScheduleService(ProgrammingLanguageService programmingLanguageService,
                                  GraphicsDrawer graphicsDrawer) {
        this.programmingLanguageService = programmingLanguageService;
        this.graphicsDrawer = graphicsDrawer;
    }

    @Async
    @Scheduled(cron = "0 20 17 * * *")
    public void saveAllVacancies() throws InterruptedException {
        HHruParser parser = new HHruParser();
        Map<ProgrammingLanguageName, List<VacancyHHruJson>> vacancies = new HashMap<>();
        for (ProgrammingLanguageName programmingLanguageName : ProgrammingLanguageName.values()) {
            vacancies.put(programmingLanguageName, parser.getAllVacanciesFromRussia(programmingLanguageName));

        }
        for (Map.Entry<ProgrammingLanguageName, List<VacancyHHruJson>> vacancy : vacancies.entrySet()) {
            programmingLanguageService.saveVacancies(vacancy.getKey(), vacancy.getValue());
        }
        graphicsDrawer.drawAllGraphics();
    }

    @Async
    @Scheduled(cron = "0 41 23 * * *")
    public void updateCountRepositories() throws MalformedURLException, InterruptedException {
        for (ProgrammingLanguageName programmingLanguageName : ProgrammingLanguageName.values()) {
            programmingLanguageService.updateCountRepositories(programmingLanguageName);
        }
    }
}
