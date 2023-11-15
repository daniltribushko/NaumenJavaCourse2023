package com.example.naumen2023.services.graphics.interfaces;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

public interface ChartDataMapper {
    CategoryDataset createDatasetMostPopular();
    PieDataset<String> createDatasetCountVacancies();
    CategoryDataset createDatasetByMiddleSalary();
}
