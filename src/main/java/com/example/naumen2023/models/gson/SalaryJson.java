package com.example.naumen2023.models.gson;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс зарплаты для преобразования json обьекта
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
@ToString
@AllArgsConstructor
public class SalaryJson {
    @SerializedName("from")
    private Integer from;

    @SerializedName("to")
    private Integer to;

    @SerializedName("currency")
    private String currency;
}
