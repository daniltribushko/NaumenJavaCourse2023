package com.example.naumen2023.models.dto.response;


import com.example.naumen2023.models.enums.Employment;
import com.example.naumen2023.models.enums.Experience;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.enums.Schedule;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 17.11.2023
 *
 * DTO для ответа на запрос на получение enums
 */
@Data
@Builder
public class GetDictionariesResponseDto {
    private List<GetDictionaryResponseDto> programmingLanguages;
    private List<GetDictionaryResponseDto> experience;
    private List<GetDictionaryResponseDto> employment;
    private List<GetDictionaryResponseDto> schedule;

    public static GetDictionariesResponseDto createResponse(){
        return GetDictionariesResponseDto.builder()
                .programmingLanguages(Arrays.stream(ProgrammingLanguageName.values())
                        .map(p -> new GetDictionaryResponseDto(p.getName()))
                        .toList()
                )
                .experience(Arrays.stream(Experience.values())
                        .map(e -> new GetDictionaryResponseDto(e.name()))
                        .toList()
                )
                .employment(Arrays.stream(Employment.values())
                        .map(e -> new GetDictionaryResponseDto(e.name()))
                        .toList()
                )
                .schedule(Arrays.stream(Schedule.values())
                        .map(s -> new GetDictionaryResponseDto(s.name()))
                        .toList()
                )
                .build();
    }
}
