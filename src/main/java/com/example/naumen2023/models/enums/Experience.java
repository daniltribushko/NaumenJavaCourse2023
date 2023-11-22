package com.example.naumen2023.models.enums;

/**
 * Опыт работы
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
public enum Experience {
    NO_EXPERIENCE,
    BETWEEN_1_AND_3,
    BETWEEN_3_AND_6,
    MORE_THAN_6;

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