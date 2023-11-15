package com.example.naumen2023.url;

import com.example.naumen2023.services.url.UrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class UrlServiceTest {
    private static final String TEST_URL = "www.google.com";
    @Test
    void addParameterTest(){
        UrlService service = new UrlService(TEST_URL);
        String testKey = "testValue1";
        service.addParameter(testKey, "1");
        String actual1 = service.getUrl();
        service.addParameter(testKey, "value24214");
        String actual2 = service.getUrl();
        service.addParameter(testKey, "1");

        Assertions.assertEquals("www.google.com?testValue1=1", actual1);
        Assertions.assertEquals("www.google.com?testValue1=value24214", actual2);
        Assertions.assertEquals("www.google.com?testValue1=1", actual1);
    }

    @Test
    void addParametersTest(){
        UrlService service = new UrlService(TEST_URL);
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("area", "113");
        parameters1.put("text", "C%23");
        parameters1.put("per_page", "23");
        parameters1.put("page", "0");
        service.addParameters(new TreeMap<>(parameters1));
        String actual1 = service.getUrl();
        parameters1.put("area", "1");
        parameters1.put("text", "Kotlin");
        parameters1.put("per_page","100");
        parameters1.put("page", "20");
        service.addParameters(new TreeMap<>(parameters1));
        String actual2 = service.getUrl();

        Assertions.assertEquals(
                "www.google.com?area=113&page=0&per_page=23&text=C%23", actual1);
        Assertions.assertEquals("www.google.com?area=1&page=20&per_page=100&text=Kotlin", actual2);
    }

    @Test
    void addPathParameterTest(){
        UrlService service = new UrlService(TEST_URL);
        service.addPathParameter("1");
        UrlService service1 = new UrlService(TEST_URL + "/");
        service1.addPathParameter("2");

        Assertions.assertEquals("www.google.com/1", service.getUrl());
        Assertions.assertEquals("www.google.com/2", service1.getUrl());
    }

    @Test
    void deleteLastPathParameterTest(){
        UrlService service = new UrlService(TEST_URL + "/1");
        service.deleteLastPathParameter();

        Assertions.assertEquals(TEST_URL, service.getUrl());

    }
}
