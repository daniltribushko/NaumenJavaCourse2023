package com.example.naumen2023.services.graphics.mapper;

import com.example.naumen2023.models.entities.AreaEntity;
import com.example.naumen2023.services.graphics.interfaces.ChartDataMapper;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.*;

/**
 * @author Tribushko Danil
 * @since 03.11.2023
 * <p>
 * Создание датасетов для регионов
 */
public class AreaChartDataMapper implements ChartDataMapper {
    private List<AreaEntity> areas;

    public AreaChartDataMapper(List<AreaEntity> areas) {
        this.areas = areas;
    }

    /**
     * Создание датасета содержащего 10 регионов с наибольшим количеством  вакансий
     *
     * @return датасет для постройки гистограммы
     */
    @Override
    public CategoryDataset createDatasetMostPopular() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //Получения списка регионов отсортированного по количеству вакансий
        List<AreaEntity> sortedAreas = sortedAreasByCountVacancy(areas);
        //Переворачиваем список отсортированных вакансий
        Collections.reverse(sortedAreas);
        //Добавляем в датасет значения первыъ 10 регионов
        for (AreaEntity area : sortedAreas.stream()
                .limit(10)
                .toList()) {
            dataset.setValue(area.getVacancies().size(), "количество вакансий", area.getName());
        }
        return dataset;
    }

    /**
     * Создание датасета отображающего соотношение количества вакансий среди регионов
     *
     * @return датасет для постройки диаграммы
     */
    @Override
    public PieDataset<String> createDatasetCountVacancies() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        List<AreaEntity> sortedAreas = sortedAreasByCountVacancy(areas);
        Collections.reverse(sortedAreas);
        Map<String, Integer> dataArea = new HashMap<>();
        areas.stream().limit(10)
                .forEach(a -> dataArea.put
                        (a.getName(), a.getVacancies().size())
                );
        int other = 0;
        for (int i = 10; i < areas.size(); i++) {
            other += areas.get(i).getVacancies().size();
        }
        dataArea.put("Другие", other);
        dataArea.forEach(dataset::setValue);

        return dataset;
    }

    /**
     * Созданение дасета содержащего 10 регионов с самой высокой средней зарплатой
     *
     * @return датасет для построения гистограммы
     */
    @Override
    public CategoryDataset createDatasetByMiddleSalary() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        areas = new ArrayList<>(areas.stream()
                .sorted(Comparator.comparing(
                                a -> ChartDataMapperHelper.getMiddleSalary(a.getVacancies())
                        )
                )
                .toList()
        );
        Collections.reverse(areas);
        areas.stream().limit(10)
                .forEach(a -> dataset.setValue(
                        ChartDataMapperHelper.getMiddleSalary(a.getVacancies()),
                        "руб", a.getName()));
        return dataset;
    }

    /**
     * Сортировка регионов по количеству вакансий
     *
     * @param list список регионов
     * @return список регионов отсортированных по количеству вакансий
     */
    private List<AreaEntity> sortedAreasByCountVacancy(List<AreaEntity> list) {
        return new ArrayList<>(
                list.stream()
                        .sorted(Comparator.comparing(AreaEntity::getVacancies,
                                        Comparator.comparing(Set::size)
                                )
                        )
                        .toList()
        );
    }
}
