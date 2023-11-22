package com.example.naumen2023.services.graphics.interfaces;

import com.example.naumen2023.models.enums.GraphicsType;
import org.jfree.chart.JFreeChart;

public interface GraphicsCreatorInterface {
    void createGraphics(JFreeChart chart, GraphicsType graphicsType, String fileName) ;
    void deleteGraphics(GraphicsType graphicsType, String fileName);
}
