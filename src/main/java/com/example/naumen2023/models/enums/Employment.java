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
    FULL("Полная занятость", "full"),
    PART("Частичная занятость", "part"),
    PROJECT("Проектная работа", "project"),
    VOLUNTEER("Волонтерство", "volunteer"),
    //Стажировка
    PROBATION("Стажировка", "probation");

    private final String ruName;
    private final String name;

    Employment(String ruName, String name){
        this.ruName = ruName;
        this.name = name;
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

