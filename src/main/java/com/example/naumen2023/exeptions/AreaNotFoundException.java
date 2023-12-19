package com.example.naumen2023.exeptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Исключение при отсуствии региона в бд
 */
@Slf4j
public class AreaNotFoundException extends GlobalAppException{
    public AreaNotFoundException(String name) {
        super(404, "Area " + name + " does not exist");
    }
}
