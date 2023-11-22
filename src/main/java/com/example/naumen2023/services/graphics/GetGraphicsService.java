package com.example.naumen2023.services.graphics;

import com.example.naumen2023.models.dto.request.GetGraphicRequestDto;
import com.example.naumen2023.models.dto.response.GetGraphicResponseDto;
import com.example.naumen2023.models.enums.GraphicsSubType;
import com.example.naumen2023.models.enums.GraphicsType;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;

public class GetGraphicsService {
    private static final String URL = "src/main/resources/graphics/";

    public static GetGraphicResponseDto getGraphics(GetGraphicRequestDto request) {
        ProgrammingLanguageName programmingLanguageName = request.getProgrammingLanguageName();
        GraphicsType type = request.getGraphicsType();
        GraphicsSubType subType = request.getGraphicsSubType();
        String url = URL +
                type.name().toLowerCase() +
                "/" + programmingLanguageName
                .getName().toLowerCase() + "/";
        String subUrl = url + subType
                .name()
                .toLowerCase()
                + "_" + programmingLanguageName
                .getName()
                .toLowerCase();

        return GetGraphicResponseDto
                .builder()
                .graphicsType(type)
                .url(subUrl)
                .programmingLanguageName(programmingLanguageName)
                .build();
    }
}
