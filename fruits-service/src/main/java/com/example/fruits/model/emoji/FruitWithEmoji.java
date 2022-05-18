package com.example.fruits.model.emoji;

import com.example.fruits.dto.FruitInfoDto;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class FruitWithEmoji {
    Integer id;
    String name;
    String emoji;
    Nutrition nutritions;

    public static FruitWithEmoji of(FruitInfoDto fruit, String emoji) {
        return FruitWithEmoji.builder()
            .id(fruit.getId())
            .name(fruit.getName())
            .emoji(emoji)
            .nutritions(fruit.getNutritions())
            .build();
    }
}
