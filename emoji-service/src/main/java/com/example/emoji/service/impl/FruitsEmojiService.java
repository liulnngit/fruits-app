package com.example.emoji.service.impl;


import com.example.emoji.model.FruitEmoji;

import java.util.List;

public interface FruitsEmojiService {
    List<FruitEmoji> findAll();

    FruitEmoji findEmoji(String name);
}
