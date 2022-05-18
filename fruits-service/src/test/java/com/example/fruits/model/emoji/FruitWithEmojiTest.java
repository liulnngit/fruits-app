package com.example.fruits.model.emoji;

import com.example.fruits.dto.FruitInfoDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FruitWithEmojiTest {

    @Test
    void givenOf_whenFruitInfoDtoAndEmoji_thenReturnFruitWithEmoji() {
        var nutrition = Nutrition.builder()
            .calories(10)
            .fat(9.0)
            .sugar(5.0)
            .carbohydrates(8.0)
            .build();
        var fruitInfoDto = FruitInfoDto.builder()
            .id(1)
            .name("Banana")
            .family("Musaceae")
            .order("Zingiberales")
            .nutritions(nutrition)
            .build();

        var actual = FruitWithEmoji.of(fruitInfoDto, "emoji");

        assertNotNull(actual);
        assertEquals("emoji", actual.getEmoji());
        assertEquals("Banana", actual.getName());
        assertEquals(nutrition, actual.getNutritions());
    }
}
