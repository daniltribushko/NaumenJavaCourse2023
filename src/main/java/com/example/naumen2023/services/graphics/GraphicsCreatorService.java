package com.example.naumen2023.services.graphics;

import com.example.naumen2023.models.enums.GraphicsType;
import com.example.naumen2023.services.graphics.interfaces.GraphicsCreatorInterface;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

/**
 * @author Tribushko Danil
 * @since 03.11.2023
 *
 * Класс для создания графиков
 */
@Slf4j
@Component
public class GraphicsCreatorService implements GraphicsCreatorInterface {
    private String url = "src/main/resources/static/graphics";
    private static final String EXTENSION = ".png";

    /**
     * Создание графика
     *
     * @param chart график
     * @param graphicsType тип графика
     * @param fileName имя файла
     */
    @Override
    public void createGraphics(JFreeChart chart, GraphicsType graphicsType, String fileName) {
        File file = new File(url + "/" + graphicsType.toString().toLowerCase() + "/" + fileName + EXTENSION);
        try {
            Files.createFile(file.toPath());
            ChartUtils.saveChartAsPNG(file, chart, 500, 500);
            //Если такой график уже есть заменяем его на новый
        } catch (FileAlreadyExistsException e){
            deleteGraphics(graphicsType, fileName);
            createGraphics(chart, graphicsType, fileName);
        } catch (IOException e){
            log.warn(e.getMessage());
        }
    }

    /**
     * Удаление графика
     *
     * @param graphicsType тип графика
     * @param fileName имя графика
     */
    @Override
    public void deleteGraphics(GraphicsType graphicsType, String fileName) {
        File file = new File(url + "/" + graphicsType.toString().toLowerCase() + "/" + fileName + EXTENSION);
        try {
            Files.delete(file.toPath());
            log.info(fileName + ": deleted");
        } catch (IOException e){
            log.warn(e.getMessage());
        }
    }
}
