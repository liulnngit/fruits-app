package com.example.fruits.model.emoji;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutrition {
    @JsonProperty("carbohydrates")
    private Double carbohydrates;
    @JsonProperty("protein")
    private Double protein;
    @JsonProperty("fat")
    private Double fat;
    @JsonProperty("calories")
    private Integer calories;
    @JsonProperty("sugar")
    private Double sugar;
}
