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
    FULL_DAY("Полный день"),
    //Сменный график
    SHIFT("Сменный график"),
    //Гибкий график
    FLEXIBLE("Гибкий график"),
    //Удаленная работа
    REMOTE("Удаленная работа"),
    //Вахтовый метод
    FLY_IN_FLY_OUT("Вахтовый метод");

    private final String ruName;

    Schedule(String ruName){
        this.ruName = ruName;
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