package com.example.naumen2023.models.dto.response;

import com.example.naumen2023.models.enums.GraphicsType;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetGraphicResponseDto {
    ProgrammingLanguageName programmingLanguageName;
    GraphicsType graphicsType;
    String url;
}
