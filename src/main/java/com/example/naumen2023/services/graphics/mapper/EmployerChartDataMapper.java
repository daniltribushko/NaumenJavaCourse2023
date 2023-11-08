package com.example.naumen2023.services.graphics.mapper;

import com.example.naumen2023.models.entities.EmployerEntity;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.*;

/**
 * @author Tribushko Danil
 * @since 03.11.2023
 *
 * Создание датасетов для графиков работодателей
 */
public class EmployerChartDataMapper implements ChartDataMapper {
    private List<EmployerEntity> employers;

    public EmployerChartDataMapper(List<EmployerEntity> employers){
        this.employers = employers;
    }

    /**
     * Создание датасета содержащего 10 работодателей с наибольшим количеством  вакансий
     *
     * @return датасет для постройки гистограммы
     */
    @Override
    public CategoryDataset createDatasetMostPopular() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //Получения списка регионов отсортированного по количеству вакансий
        List<EmployerEntity> sortedAreas = sortedEmployersByCountVacancy(employers);
        //Переворачиваем список отсортированных вакансий
        Collections.reverse(sortedAreas);
        //Добавляем в датасет значения первыъ 10 регионов
        for (EmployerEntity employer : sortedAreas.stream()
                .limit(10)
                .toList()) {
            dataset.setValue(employer.getVacancies().size(), "количество вакансий", employer.getName());
        }
        return dataset;
    }

    /**
     * Создание датасета отображающего соотношение количества вакансий среди работодателей
     *
     * @return датасет для постройки диаграммы
     */
    @Override
    public PieDataset<String> createDatasetCountVacancies() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        List<EmployerEntity> sortedEmployers = sortedEmployersByCountVacancy(employers);
        Collections.reverse(sortedEmployers);
        Map<String, Integer> dataEmployer = new HashMap<>();
        employers.stream().limit(10).forEach(e -> dataEmployer.put(e.getName(), e.getVacancies().size()));
        int other = 0;
        for (int i = 10; i < employers.size(); i++) {
            other += employers.get(i).getVacancies().size();
        }
        dataEmployer.put("Другие", other);
        dataEmployer.forEach(dataset::setValue);

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
        employers = new ArrayList<>(employers.stream()
                .sorted(Comparator.comparing(
                                e -> ChartDataMapperHelper.getMiddleSalary(e.getVacancies())
                        )
                )
                .toList()
        );
        Collections.reverse(employers);
        employers.stream().limit(10)
                .forEach(e -> dataset.setValue(
                        ChartDataMapperHelper.getMiddleSalary(e.getVacancies()),
                        "руб", e.getName()));
        return dataset;
    }

    /**
     * Сортировка регионов по количеству вакансий
     *
     * @param list список регионов
     * @return список регионов отсортированных по количеству вакансий
     */
    private List<EmployerEntity> sortedEmployersByCountVacancy(List<EmployerEntity> list) {
        return new ArrayList<>(
                list.stream()
                        .sorted(Comparator.comparing(EmployerEntity::getVacancies,
                                        Comparator.comparing(Set::size)
                                )
                        )
                        .toList()
        );
    }
}
