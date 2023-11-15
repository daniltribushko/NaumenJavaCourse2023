package com.example.naumen2023.exeptions;

import lombok.Data;

@Data
public class GlobalException extends RuntimeException{
    protected String name;

    public GlobalException(String name){
        this.name = name;
    }
}
