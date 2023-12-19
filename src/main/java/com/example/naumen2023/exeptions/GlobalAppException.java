package com.example.naumen2023.exeptions;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс исключение приложения при запросах к api
 */
@Data
public abstract class GlobalAppException extends RuntimeException{
    protected Integer status;
    protected String message;
    protected LocalDateTime dateTime;
    public GlobalAppException(Integer status, String message){
        this.status = status;
        this.message = message;
        dateTime = LocalDateTime.now();
    }

}
