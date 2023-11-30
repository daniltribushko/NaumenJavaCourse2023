package com.example.naumen2023.models.enums;

import lombok.Getter;

/**
 * Опыт работы
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
public enum Experience {
    NO_EXPERIENCE("Без опыта работы"),
    BETWEEN_1_AND_3("От 1 до 3 лет"),
    BETWEEN_3_AND_6("От 3 до 6 лет"),
    MORE_THAN_6("От 6 лет");

    private final String ruName;

    Experience(String ruName){
        this.ruName = ruName;
    }

    public static Experience getExperienceFromString(String experience){
        Experience result;
        switch (experience){
            case "noExperience" -> result = NO_EXPERIENCE;
            case "between1And3" -> result = BETWEEN_1_AND_3;
            case "between3And6" -> result = BETWEEN_3_AND_6;
            case "moreThan6" -> result = MORE_THAN_6;
            default -> result = null;
        }
        return result;
    }
}