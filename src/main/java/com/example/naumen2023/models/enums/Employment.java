package com.example.naumen2023.models.enums;

import lombok.Getter;

/**
 * Тип работы
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
public enum Employment {
    FULL("Полная занятость"),
    PART("Частичная занятость"),
    PROJECT("Проектная работа"),
    VOLUNTEER("Волонтерство"),
    //Стажировка
    PROBATION("Стажировка");

    private final String ruName;

    Employment(String ruName){
        this.ruName = ruName;
    }

    public static Employment getEmploymentFromString(String employment) {
        Employment result;
        switch (employment) {
            case "full" -> result = FULL;
            case "part" -> result = PART;
            case "project" -> result = PROJECT;
            case "volunteer" -> result = VOLUNTEER;
            case "probation" -> result = PROBATION;
            default -> result = null;
        }
        return result;
    }
}

