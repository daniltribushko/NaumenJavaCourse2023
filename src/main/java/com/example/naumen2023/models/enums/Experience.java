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
    NO_EXPERIENCE("noExperience","Без опыта работы"),
    BETWEEN_1_AND_3("between1And3","От 1 до 3 лет"),
    BETWEEN_3_AND_6("between3And6","От 3 до 6 лет"),
    MORE_THAN_6("moreThan6","От 6 лет");

    private final String ruName;
    private final String name;

    Experience(String name, String ruName){
        this.ruName = ruName;
        this.name = name;
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