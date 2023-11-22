package com.example.naumen2023.models.dto.request;

import com.example.naumen2023.models.enums.GraphicsSubType;
import com.example.naumen2023.models.enums.GraphicsType;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Serializable;

@Data
public class GetGraphicRequestDto implements Serializable {
    ProgrammingLanguageName programmingLanguageName;
    GraphicsType graphicsType;
    GraphicsSubType graphicsSubType;
}
