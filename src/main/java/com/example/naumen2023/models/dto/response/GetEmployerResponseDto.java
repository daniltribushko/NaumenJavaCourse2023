package com.example.naumen2023.models.dto.response;

import com.example.naumen2023.models.entities.EmployerEntity;
import lombok.Builder;
import lombok.Data;

/**
 * @author Tribushko Danil
 * @since 17.11.2023
 *
 * DTO для ответа на запрос на получение работодателя
 */
@Data
@Builder
public class GetEmployerResponseDto {
    private String idHHru;
    private String name;
    private String url;
    private String logo;

    public static GetEmployerResponseDto mapFromEntity(EmployerEntity employer){
        return GetEmployerResponseDto.builder()
                .idHHru(employer.getIdHHru())
                .name(employer.getName())
                .url(employer.getUrl())
                .logo(employer.getLogo())
                .build();
    }
}
