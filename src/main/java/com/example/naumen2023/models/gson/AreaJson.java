package com.example.naumen2023.models.gson;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс региона с hh.ru для преобразования json обьекта
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
@ToString
@AllArgsConstructor
public class AreaJson {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;
}
