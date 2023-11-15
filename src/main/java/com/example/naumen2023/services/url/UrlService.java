package com.example.naumen2023.services.url;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис для работы с url адресом
 *
 * @author Tribushko Danil
 * @since 27.10.2023
 */
@Slf4j
public class UrlService implements UrlInterface {
    @Getter
    private String url;
    private static final String AMPERSAND = "&";
    private static final String QUESTION = "?";

    public UrlService(String url) {
        this.url = url;
    }

    /**
     * Добавление параметра в виде key=value в адрес
     *
     * @param key   ключ параметра
     * @param value значение параметра
     */
    @Override
    public void addParameter(String key, String value) {
        //Если параметер уже указан в адресе меняем его, иначе добавляем
        if (isKeyPresent(key)) {
            replaceParameter(key, value);
        } else {
            addQuestion();
            addAmpersand();
            url = String.format("%s%s=%s", url, key, value);
        }
    }

    /**
     * Добавление нескольких параметров
     *
     * @param parameters словарь параметров
     */
    @Override
    public void addParameters(Map<String, String> parameters) {
        String key;
        //Проходимся по параметрам, если параметер уже указан, то заменяем его иначе добавляем в адрес
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            key = parameter.getKey();
            if (isKeyPresent(key)) {
                replaceParameter(key, parameter.getValue());
            } else {
                addQuestion();
                addAmpersand();
                url = String.format("%s%s=%s", url, key, parameter.getValue());
            }
        }
    }

    /**
     * Добавление параметра в часть url адреса
     *
     * @param value значение, которое надо вставить
     */
    @Override
    public void addPathParameter(String value) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        url += value;
    }

    /**
     * Удаление последней части url адреса
     */
    @Override
    public void deleteLastPathParameter() {
        String[] mas = url.split("/");
        url = url.replace("/" + mas[mas.length - 1], "");
    }

    /**
     * Добавление символа & в url адрес при добавлении нового параметра
     */
    private void addAmpersand() {
        if (!url.endsWith(AMPERSAND) && url.contains("=")) {
            url += AMPERSAND;
        }
    }

    /**
     * Добавление символа ? в url адрес при первом добавлении параметров
     */
    private void addQuestion() {
        if (!url.contains(QUESTION)) {
            url += QUESTION;
        }
    }

    /**
     * Проверка наличие параметра в url адресе
     *
     * @param key ключ параметра
     * @return значение, которое указывает содержится ли ключ в адресе
     */
    private boolean isKeyPresent(String key) {
        return Pattern.compile(String.format("[&\\?]%s=", key))
                .matcher(url)
                .find();
    }

    /**
     * Проверка наличие параметра в части url адресе
     *
     * @param key ключ параметра
     * @return значение, которое указывает содержится ли ключ в адресе
     */
    private boolean isKeyPresent(String url, String key) {
        return Pattern.compile(String.format("^%s", key))
                .matcher(url)
                .find();
    }

    /**
     * Замена значения параметра в url адресе
     *
     * @param key   ключ параметра
     * @param value значение параметра
     */
    private void replaceParameter(String key, String value) {
        //Проверяем наличие ключа в адресе при помощи регулярного выражения
        Matcher matcher = Pattern.compile(
                        String.format("[&\\?]%s=[\\w%s]*", key, "%")
                )
                .matcher(url);
        if (matcher.find()) {
            //Получаем старое значение параметра в виде key=value
            String oldKeyValue = url.substring(matcher.start() + 1, matcher.end());
            //Удаляем символы & если они есть
            if (oldKeyValue.contains("&")) {
                oldKeyValue = oldKeyValue.replace(AMPERSAND, "");
            }
            //Создаем переменную содержащую новое значение параметра
            String newKeyValue = String.format("%s=%s", key, value);
            //Разбиваем url адрес на части, и добавляем их в builder, если дошли до старогопараметра, добавляем новый
            StringBuilder newUrl = new StringBuilder();
            String[] urlParts = url.split("&");
            for (String urlPart : urlParts) {
                if (isKeyPresent(urlPart, key)) {
                    newUrl.append(newKeyValue);
                } else {
                    newUrl.append(urlPart);
                }
                newUrl.append("&");
            }
            //Удаляем последний амперсанд
            newUrl.delete(newUrl.length() - 1, newUrl.length());
            url = newUrl.toString();
            log.info(String.format("ReplaceParameter: Old parameter=%s - New parameter=%s",
                    oldKeyValue, newKeyValue));
        }
    }
}
