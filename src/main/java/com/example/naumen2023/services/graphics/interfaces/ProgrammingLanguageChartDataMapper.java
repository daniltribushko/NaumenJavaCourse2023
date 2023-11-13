package com.example.naumen2023.services.graphics.interfaces;

import com.example.naumen2023.models.entities.SkillEntity;
import com.example.naumen2023.models.entities.VacancyHHruEntity;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.*;

public interface ProgrammingLanguageChartDataMapper {
    static CategoryDataset createDatasetMostPopularSkills(ProgrammingLanguageName programmingLanguageName,
                                                          Set<VacancyHHruEntity> vacancies) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<SkillEntity, Integer> data = new HashMap<>();
        vacancies.stream()
                .filter(v ->
                        v.getProgrammingLanguage()
                                .getName().equals(programmingLanguageName)
                )
                .forEach(v -> {
                            for (SkillEntity skill : v.getSkills()) {
                                Integer value = data.get(skill);
                                if (value != null) {
                                    data.put(skill, value + 1);
                                    System.out.println(value + 1);
                                } else {
                                    data.put(skill, 1);
                                }
                            }
                        }
                );
        data.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .skip(data.size() - 10)
                .forEach(d -> dataset.setValue(
                                d.getValue(),
                                "Количество",
                                d.getKey()
                                        .getName()
                        )
                );
        return dataset;
    }
}
