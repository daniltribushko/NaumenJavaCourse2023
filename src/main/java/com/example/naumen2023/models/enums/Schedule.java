package com.example.naumen2023.models.enums;

/**
 * График работы
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
public enum Schedule {
    FULL_DAY,
    //Сменный график
    SHIFT,
    //Гибкий график
    FLEXIBLE,
    //Удаленная работа
    REMOTE,
    //Вахтовый метод
    FLY_IN_FLY_OUT;

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