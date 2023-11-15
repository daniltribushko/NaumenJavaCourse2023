package com.example.naumen2023.services.api;

import java.util.Optional;

public interface ApiInterface {
    void addRequestProperty(String key, String value);
    Optional<StringBuilder> getResponse();
}
