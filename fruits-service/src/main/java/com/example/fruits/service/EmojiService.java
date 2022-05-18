package com.example.fruits.service;

import com.example.fruits.model.emoji.FruitEmoji;

import java.util.Map;

public interface EmojiService {
    FruitEmoji getFruitEmoji(String name);

    Map<String, String> getEmojiMap();
}
