package com.example.naumen2023.exeptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Исключение при неверном указании типа опыта работы
 */
@Slf4j
public class WrongExperienceException extends GlobalAppException{
    public WrongExperienceException() {
        super(400, "Wrong experience");
    }
}
