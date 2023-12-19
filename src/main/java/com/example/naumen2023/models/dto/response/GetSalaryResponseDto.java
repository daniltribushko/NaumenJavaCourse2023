package com.example.naumen2023.models.dto.response;

import com.example.naumen2023.models.entities.SalaryEntity;
import lombok.Builder;
import lombok.Data;

/**
 * @author Tribushko Danil
 * @since 17.11.2023
 *
 * DTO для ответа на запрос на получение зарплаты
 */
@Data
@Builder
public class GetSalaryResponseDto {
    private Integer from;
    private Integer to;
    private String currency;

    public static GetSalaryResponseDto mapFromEntity(SalaryEntity salary){
        if (salary != null) {
            return GetSalaryResponseDto.builder()
                    .from(salary.getFrom())
                    .to(salary.getTo())
                    .currency(salary.getCurrency())
                    .build();
        } else {
            return null;
        }
    }
}
