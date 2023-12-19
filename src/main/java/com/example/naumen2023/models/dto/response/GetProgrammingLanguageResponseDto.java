package com.example.naumen2023.models.dto.response;

import com.example.naumen2023.models.entities.ProgrammingLanguageEntity;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProgrammingLanguageResponseDto {
    private ProgrammingLanguageName name;
    private Integer countRepositories;
    private Integer countVacancies;

    public static GetProgrammingLanguageResponseDto mapFromEntity(ProgrammingLanguageEntity programmingLanguage){
        return GetProgrammingLanguageResponseDto
                .builder()
                .name(programmingLanguage.getName())
                .countRepositories(programmingLanguage.getCountRepositories())
                .countVacancies(programmingLanguage.getVacancies().size())
                .build();
    }
}
