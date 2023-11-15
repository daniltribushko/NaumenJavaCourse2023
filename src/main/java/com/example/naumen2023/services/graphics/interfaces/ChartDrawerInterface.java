package com.example.naumen2023.services.graphics.interfaces;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

public interface ChartDrawerInterface {
    JFreeChart createPieChart(String titleChart, PieDataset dataset);
    JFreeChart createCategoryChart(String title, String titleX, String titleY, CategoryDataset dataset);
    JFreeChart createTimeSeriesChart(XYDataset dataset);
}
