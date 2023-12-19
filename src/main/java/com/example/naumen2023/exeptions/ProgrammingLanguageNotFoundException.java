package com.example.naumen2023.exeptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Исключение при отсуствии языка программирования в бд
 */
@Slf4j
public class ProgrammingLanguageNotFoundException extends GlobalAppException{
    public ProgrammingLanguageNotFoundException(String programmingLanguage) {
        super(404, "Programming Language " + programmingLanguage + " does not exist");
    }
}
