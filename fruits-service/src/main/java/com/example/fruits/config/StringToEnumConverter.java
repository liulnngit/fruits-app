package com.example.fruits.config;

import com.example.fruits.model.NutritionType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, NutritionType> {
    @Override
    public NutritionType convert(String source) {
        return NutritionType.valueOf(source.toUpperCase());
    }
}