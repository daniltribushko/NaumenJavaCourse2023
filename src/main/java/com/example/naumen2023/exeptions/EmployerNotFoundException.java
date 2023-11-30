package com.example.naumen2023.exeptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Исключение при отсуствии работодателя в бд
 */
@Slf4j
public class EmployerNotFoundException extends GlobalAppException{
    public EmployerNotFoundException(String name) {
        super(404, "Employer " + name + " does not exist");
    }
}
