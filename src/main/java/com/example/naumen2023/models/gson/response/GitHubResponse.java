package com.example.naumen2023.models.gson.response;

import com.google.gson.annotations.SerializedName;

public record GitHubResponse(@SerializedName("total_count") Integer count) {
}
