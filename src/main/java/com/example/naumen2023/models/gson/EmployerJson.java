package com.example.naumen2023.models.gson;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс работадателя для преобразования json обьекта
 *
 * @author Tribushko Danil
 * @since 26.10.2023
 */
@Getter
@ToString
@AllArgsConstructor
public class EmployerJson {
    @SerializedName("id")
    private String idHHru;

    @SerializedName("name")
    private String name;

    @SerializedName("alternate_url")
    private String url;

    @SerializedName("logo_urls")
    private Logo logo;

    public record Logo(@SerializedName("original") String url){}
}
