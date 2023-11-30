package com.example.naumen2023.exeptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Исключение при неверном указании типа графика работы
 */
@Slf4j
public class WrongScheduleException extends GlobalAppException{
    public WrongScheduleException() {
        super(400, "Wrong schedule");
    }
}
