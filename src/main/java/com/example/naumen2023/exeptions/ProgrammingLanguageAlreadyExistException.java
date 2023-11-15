package com.example.naumen2023.exeptions;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProgrammingLanguageAlreadyExistException extends GlobalException{

    public ProgrammingLanguageAlreadyExistException(ProgrammingLanguageName programmingLanguageName) {
        super(programmingLanguageName.getName() + ": AlreadyExist in db");
        log.warn(programmingLanguageName.getName() + ": AlreadyExist in db");
    }
}
