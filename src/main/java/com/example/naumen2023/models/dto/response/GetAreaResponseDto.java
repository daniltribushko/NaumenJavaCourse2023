package com.example.naumen2023.models.dto.response;


import com.example.naumen2023.models.entities.AreaEntity;
import lombok.Builder;
import lombok.Data;

/**
 * @author Tribushko Danil
 * @since 17.11.2023
 *
 * DTO для ответа на запрос на получение региона
 */
@Data
@Builder
public class GetAreaResponseDto {
    private String idHHru;
    private String name;

    public static GetAreaResponseDto mapFromEntity(AreaEntity area){
        return GetAreaResponseDto.builder()
                .idHHru(area.getHhRuId())
                .name(area.getName())
                .build();
    }
}
