package com.example.naumen2023.models.enums;

import lombok.Getter;

/**
 * График работы
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
public enum Schedule {
    FULL_DAY("Полный день","fullDay"),
    //Сменный график
    SHIFT("Сменный график", "shift"),
    //Гибкий график
    FLEXIBLE("Гибкий график", "flexible"),
    //Удаленная работа
    REMOTE("Удаленная работа", "remote"),
    //Вахтовый метод
    FLY_IN_FLY_OUT("Вахтовый метод","flyInFlyOut");

    private final String ruName;
    private final String name;
    Schedule(String ruName, String name){
        this.ruName = ruName;
        this.name = name;
    }

    public static Schedule getScheduleFromString(String schedule){
        Schedule result;
        switch (schedule){
            case "fullDay" -> result = FULL_DAY;
            case "shift" -> result = SHIFT;
            case "flexible" -> result = FLEXIBLE;
            case "remote" -> result = REMOTE;
            case "flyInFlyOut" -> result = FLY_IN_FLY_OUT;
            default -> result = null;
        }
        return result;
    }
}