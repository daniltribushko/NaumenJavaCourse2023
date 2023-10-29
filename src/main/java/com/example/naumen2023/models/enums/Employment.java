package com.example.naumen2023.models.enums;

/**
 * Тип работы
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
public enum Employment {
    FULL,
    PART,
    PROJECT,
    VOLUNTEER,
    //Стажировка
    PROBATION;

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

