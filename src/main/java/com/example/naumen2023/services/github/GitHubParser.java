package com.example.naumen2023.services.github;


import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.gson.response.GitHubResponse;
import com.example.naumen2023.services.api.ApiService;
import com.example.naumen2023.services.properties.PropertyInterface;
import com.example.naumen2023.services.properties.PropertyService;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Класс для парсинга GitHub
 *
 * @author Tribushko Danil
 * @since 30.10.2023
 */
@Slf4j
@Component
@PropertySource("classpath:application.yaml")
public class GitHubParser {

    private String url = "https://api.github.com/search/repositories?q=";

    /**
     * Получение количества репозиториев имеющих количество звезд больше либо равное 100
     *
     * @param programmingLanguageName название языка программирования
     * @return количество репозиториев
     */
    public Integer getCountRepositories(ProgrammingLanguageName programmingLanguageName) throws InterruptedException,
            MalformedURLException {

        ApiService service = new ApiService(new URL(url + "language%3A" + programmingLanguageName.getUrlName()));
        PropertyInterface propertyInterface = new PropertyService("myproperties.properties");
        String token = propertyInterface.getValue("github.token");
        service.addRequestProperty("Authorization", "Bearer " + token);
        StringBuilder response = service.getResponse().orElse(null);
        Thread.sleep(100);
        if (response == null) {
            log.error("Response is null");
            return -1;
        } else {
            return new GsonBuilder().create().fromJson(response.toString(), GitHubResponse.class).count();
        }
    }
}
