package com.example.naumen2023.services.hh.ru;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.services.programminglanguage.ProgrammingLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class UpdaterVacancies {
    private ProgrammingLanguageService programmingLanguageService;

    @Autowired
    public UpdaterVacancies(ProgrammingLanguageService programmingLanguageService){
        this.programmingLanguageService = programmingLanguageService;
    }

    @Scheduled( cron = "0 0 12 * * *")
    private void saveAllVacancies(){
        HHruParser parser = new HHruParser();
        for (ProgrammingLanguageName programmingLanguageName : ProgrammingLanguageName.values()){
            programmingLanguageService.addVacancies(programmingLanguageName,
                    parser.getAllVacanciesFromRussia(programmingLanguageName));
        }

    }
}
