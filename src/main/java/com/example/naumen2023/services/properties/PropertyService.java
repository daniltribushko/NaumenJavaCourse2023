package com.example.naumen2023.services.properties;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertyService implements PropertyInterface{
    private final String fileName;
    private final String directory = "src/main/resources/";
    private Properties properties;

    public PropertyService(String fileName){
        this.fileName = fileName;
        open();
    }

    private void open(){
        File file = new File(directory + fileName);
        properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(file)){
            properties.load(inputStream);
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }
    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
