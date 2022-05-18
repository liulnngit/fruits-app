package com.example.fruits.service;

import com.example.fruits.dto.FruitInfoDto;
import com.example.fruits.model.NutritionType;
import com.example.fruits.model.emoji.FruitWithEmoji;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FruitsService {
    Page<FruitInfoDto> getAllFruits(Pageable pageable);

    FruitWithEmoji getFruitWithEmoji(String name);

    Page<FruitWithEmoji> getAllFruitsWithEmojis(Pageable pageable);

    Page<FruitWithEmoji> getTopFruitsWithEmojis(Pageable pageable, NutritionType nutritionType, int topCount);
}
