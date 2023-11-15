package com.example.naumen2023.services.graphics.mapper;

import com.example.naumen2023.models.entities.SalaryEntity;
import com.example.naumen2023.models.entities.VacancyHHruEntity;

import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 06.11.2023
 *
 * Класс для вспомогательных методов для работы с графиками
 */
public class ChartDataMapperHelper {
    /**
     * Получение средней зарплаты среди вакансий
     *
     * @param vacancies множество вакансий
     * @return средняя зарплата
     */
    protected static Integer getMiddleSalary(Set<VacancyHHruEntity> vacancies) {
        int result = 0;
        int count = 0;
        for (VacancyHHruEntity vacancy : vacancies) {
            SalaryEntity salary = vacancy.getSalary();
            if (salary != null && salary.getCurrency().equals("RUR")) {
                count++;
                if (salary.getFrom() != null && salary.getTo() != null) {
                    result += (salary.getFrom() + salary.getTo()) / 2;
                } else if (salary.getFrom() != null) {
                    result += salary.getFrom();
                } else {
                    result += salary.getTo();
                }

            }
        }
        if (count != 0) {
            return result / count;
        } else {
            return 0;
        }
    }
}
