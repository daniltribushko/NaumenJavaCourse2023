package com.example.naumen2023.services.github;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.gson.response.GitHubResponse;
import com.example.naumen2023.services.api.ApiService;
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
 * @since 29.10.2023
 */
@Slf4j
@Component
@PropertySource("classpath:secret.yaml")
public class GitHubParser {
    private static final String URL = "https://api.github.com/search/repositories?q=stars%3A%3E=100";

    @Value("${github.token}")
    private static String TOKEN;

    /**
     * Получение количества репозиториев имеющих количество звезд больше либо равное 100
     *
     * @param programmingLanguageName название языка программирования
     * @return количество репозиториев
     */
    public static Integer getCountRepositories(ProgrammingLanguageName programmingLanguageName) {
        try {

            ApiService service = new ApiService(new URL(URL + "+language%3A" + programmingLanguageName.getUrlName()));
            service.addRequestProperty("Authorization", "Bearer: " + TOKEN);
            StringBuilder response = service.getResponse().orElse(null);

            if (response == null) {
                log.error("Response is null");
                return -1;
            } else {
                return new GsonBuilder().create().fromJson(response.toString(), GitHubResponse.class).count();
            }
        } catch (MalformedURLException e){
            log.error(e.getMessage());
            return -1;
        }
    }
}
