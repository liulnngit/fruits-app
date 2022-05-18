package com.example.fruits.model.emoji;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class FruitEmoji {
    long id;
    String name;
    String emoji;
}
