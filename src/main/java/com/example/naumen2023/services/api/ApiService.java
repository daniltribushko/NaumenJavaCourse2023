package com.example.naumen2023.services.api;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Slf4j
@Getter
public class ApiService implements ApiInterface {
    private HttpURLConnection connection;

    public ApiService(URL url) {
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            connection = null;
            log.error(e.getMessage());
        }
    }

    /**
     * Добавление свойства к подключению
     *
     * @param key   ключ совйства
     * @param value значение свойства
     */
    @Override
    public void addRequestProperty(String key, String value) {
        if (connection != null) {
            connection.setRequestProperty(key, value);
        } else {
            log.error("Connection is null");
        }
    }

    /**
     * Получение ответа на запрос
     *
     * @return ответ на запрос
     */
    @Override
    public Optional<StringBuilder> getResponse() {
        StringBuilder response = new StringBuilder();
        //Получаем поток данных и записываем данные в StringBuilder
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            log.info(connection.getURL().toString());
            log.info(String.format("Response coed: %d", connection.getResponseCode()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            response = null;
            log.error(e.getMessage());
        }
        return Optional.ofNullable(response);
    }
}
