package com.example.fruits.service.impl;

import com.example.fruits.client.ExternalFruitsClient;
import com.example.fruits.dto.FruitInfoDto;
import com.example.fruits.model.NutritionType;
import com.example.fruits.model.emoji.FruitWithEmoji;
import com.example.fruits.service.EmojiService;
import com.example.fruits.service.FruitsService;
import com.example.fruits.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FruitsServiceImpl implements FruitsService {
    private final ExternalFruitsClient fruitsClient;
    private final EmojiService emojiService;

    @Autowired
    public FruitsServiceImpl(ExternalFruitsClient fruitsClient,
                             EmojiService emojiService) {
        this.fruitsClient = fruitsClient;
        this.emojiService = emojiService;
    }

    @Override
    public Page<FruitInfoDto> getAllFruits(Pageable pageable) {
        var allFruits = fruitsClient.getAllFruits();

        return PageUtils.toPage(allFruits, pageable);
    }

    @Override
    public FruitWithEmoji getFruitWithEmoji(String name) {
        log.info("Going to get fruit with name {}", name);

        var fruitInfoDto = fruitsClient.getFruit(name);
        var fruitEmoji = emojiService.getFruitEmoji(name);

        return FruitWithEmoji.of(fruitInfoDto, fruitEmoji.getEmoji());
    }

    @Override
    public Page<FruitWithEmoji> getAllFruitsWithEmojis(Pageable pageable) {
        log.info("Going to get fruits with emojis");

        var allFruits = fruitsClient.getAllFruits();
        var emojiMap = emojiService.getEmojiMap();

        var fruitWithEmojiList = allFruits.stream()
            .map(fruit -> FruitWithEmoji.of(fruit, emojiMap.get(fruit.getName())))
            .collect(Collectors.toList());

        return PageUtils.toPage(fruitWithEmojiList, pageable);
    }

    @Override
    public Page<FruitWithEmoji> getTopFruitsWithEmojis(Pageable pageable, NutritionType nutritionType, int topCount) {
        log.info("Going to get top {} by {} fruits with emojis", topCount, nutritionType.name().toLowerCase());

        var allFruits = fruitsClient.getAllFruits();
        var emojiMap = emojiService.getEmojiMap();

        var fruitWithEmojiList = allFruits.stream()
            .map(fruit -> FruitWithEmoji.of(fruit, emojiMap.get(fruit.getName())))
            .sorted(Comparator.comparing(nutritionType::getNutrition).reversed())
            .limit(topCount)
            .collect(Collectors.toList());

        return PageUtils.toPage(fruitWithEmojiList, pageable);
    }
}
