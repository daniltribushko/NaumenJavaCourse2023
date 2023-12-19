package com.example.naumen2023.exeptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Исключение при неверном указании типа занятости
 */
@Slf4j
public class WrongEmploymentException extends GlobalAppException{
    public WrongEmploymentException() {
        super(400, "Wrong employment");
    }
}
