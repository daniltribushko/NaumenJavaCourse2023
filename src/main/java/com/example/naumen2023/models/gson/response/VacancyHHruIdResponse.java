package com.example.naumen2023.models.gson.response;

import com.example.naumen2023.models.gson.VacancyParameterId;
import com.google.gson.annotations.SerializedName;

public record VacancyHHruIdResponse(@SerializedName("items") VacancyParameterId[] ids) {
}
