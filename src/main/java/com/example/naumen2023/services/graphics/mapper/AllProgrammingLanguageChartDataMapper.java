package com.example.naumen2023.services.graphics.mapper;

import com.example.naumen2023.models.entities.ProgrammingLanguageEntity;
import com.example.naumen2023.models.entities.SkillEntity;
import com.example.naumen2023.models.entities.VacancyHHruEntity;
import com.example.naumen2023.services.graphics.interfaces.AllProgrammingLanguagesChartDataMapperInterface;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllProgrammingLanguageChartDataMapper implements AllProgrammingLanguagesChartDataMapperInterface {
    private List<ProgrammingLanguageEntity> programmingLanguageEntities;

    public AllProgrammingLanguageChartDataMapper(List<ProgrammingLanguageEntity> programmingLanguageEntities) {
        this.programmingLanguageEntities = programmingLanguageEntities;
    }

    @Override
    public CategoryDataset createDatasetCountSkills() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<SkillEntity, Integer> data = new HashMap<>();
        programmingLanguageEntities.forEach(p -> {
            for (VacancyHHruEntity vacancy : p.getVacancies()) {
                for (SkillEntity skill : vacancy.getSkills()) {
                    Integer value = data.get(skill);
                    if (value == null) {
                        data.put(skill, 1);
                    } else {
                        data.put(skill, value + 1);
                    }
                }
            }
        });
        data.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .skip(data.size() - 30)
                .forEach(d -> dataset.setValue(
                                d.getValue(),
                                "Количество",
                                d.getKey()
                                        .getName()
                        )
                );
        return dataset;
    }

    @Override
    public CategoryDataset createDatasetMostPopular() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        programmingLanguageEntities.forEach(p -> dataset.setValue(p.getVacancies().size(),
                "Количество вакансий", p.getName()));
        return dataset;
    }

    @Override
    public PieDataset<String> createDatasetCountVacancies() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        programmingLanguageEntities.forEach(p -> dataset.setValue(
                p.getName()
                        .getName(),
                p.getVacancies()
                        .size()));
        return dataset;
    }

    @Override
    public CategoryDataset createDatasetByMiddleSalary() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        programmingLanguageEntities.forEach(p -> dataset.setValue(
                ChartDataMapperHelper
                        .getMiddleSalary(p.getVacancies()),
                "Зарплата",
                p.getName()
                        .getName())
        );
        return dataset;
    }
}
