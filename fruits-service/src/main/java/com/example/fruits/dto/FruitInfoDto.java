package com.example.fruits.dto;

import com.example.fruits.model.emoji.Nutrition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FruitInfoDto {
    @JsonProperty("genus")
    private String genus;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("family")
    private String family;
    @JsonProperty("order")
    private String order;
    @JsonProperty("nutritions")
    private Nutrition nutritions;
}
