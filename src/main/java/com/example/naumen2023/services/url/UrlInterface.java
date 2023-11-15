package com.example.naumen2023.services.url;

import java.util.Map;

public interface UrlInterface {
    void addParameter(String key, String value);
    void addParameters(Map<String, String> parameters);
    void addPathParameter(String value);
    void deleteLastPathParameter();
}
