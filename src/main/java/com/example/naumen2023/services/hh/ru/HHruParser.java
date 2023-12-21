package com.example.naumen2023.services.hh.ru;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.gson.VacancyHHruJson;
import com.example.naumen2023.models.gson.VacancyParameterId;
import com.example.naumen2023.models.gson.response.VacancyHHruIdResponse;
import com.example.naumen2023.services.api.ApiService;
import com.example.naumen2023.services.url.UrlService;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Класс дял получения вакансий с hh.ru api
 *
 * @author Tribushko Danil
 * @since 29.10.2023
 */
@Slf4j
public class HHruParser implements HHruParserInterface {
    //id России
    private static final String AREA_ID = "113";
    //Количество вакансий на одной странице
    private static final String PER_PAGE = "100";
    //Количество страниц
    private static final Integer COUNT_PAGE = 1;
    //АДрес для получения вакансий при помощи hh.ru api
    private static final String URL = "https://api.hh.ru/vacancies";

    /**
     * получение списка идентификаторов вакансий
     *
     * @param programmingLanguage язык программирования, по которому будут искаться вакансии
     * @return список идентификаторов вакансий
     */
    private List<String> getVacanciesIds(ProgrammingLanguageName programmingLanguage) {
        List<String> result = new ArrayList<>();
        UrlService service = new UrlService(URL);
        //Добавляем параметры в url адрес
        Map<String, String> parameters = Map.of(
                "area", AREA_ID,
                "text", programmingLanguage.getUrlName(),
                "per_page", PER_PAGE
        );
        service.addParameters(parameters);
        ApiService apiService;
        for (int i = 0; i < COUNT_PAGE; i++) {
            //Добавляем сраницу
            service.addParameter("page", String.valueOf(i));
            try {
                //Отправляем запрос на получение вакансии с задержкой пол секунды
                apiService = new ApiService(new URL(service.getUrl()));
                StringBuilder response = apiService.getResponse().orElse(null);
                Thread.sleep(500);
                if (response == null) {
                    log.warn("Response is null");
                    break;
                } else {
                    //Если на странице закончились вакансии, то завершаем проход по страницам
                    VacancyParameterId[] ids = new GsonBuilder()
                            .create()
                            .fromJson(String.valueOf(response), VacancyHHruIdResponse.class)
                            .ids();
                    if (ids.length == 0) {
                        log.info("Все вакансии получены");
                        break;
                    }
                    for (VacancyParameterId id : ids) {
                        result.add(id.id());
                    }
                }
            } catch (MalformedURLException | InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }

    /**
     * Получение вакансий с hh.ru по определенному языку программирования
     *
     * @param programmingLanguage язык программирования вакансий
     * @return список вакансий
     */
    @Override
    public List<VacancyHHruJson> getAllVacanciesFromRussia(ProgrammingLanguageName programmingLanguage) {
        List<String> ids = getVacanciesIds(programmingLanguage);
        List<VacancyHHruJson> result = new ArrayList<>(ids.size());
        try {
            UrlService service = new UrlService(URL);
            ApiService apiService;
            int countIds = ids.size();
            for (int i = 0; i < countIds; i++) {
                service.addPathParameter(ids.get(i));
                apiService = new ApiService(new URL(service.getUrl()));
                service.deleteLastPathParameter();
                StringBuilder response = apiService.getResponse().orElse(null);
                Thread.sleep(500);
                if (response != null){
                    result.add(new GsonBuilder()
                            .create()
                            .fromJson(response.toString(), VacancyHHruJson.class)
                    );
                    log.info(programmingLanguage.getName() + " : Вакансия получена " + (i + 1) + "/" + countIds);
                } else {
                    log.warn("response is null");
                }
            }
        } catch (MalformedURLException | InterruptedException e){
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return result;
    }
}
