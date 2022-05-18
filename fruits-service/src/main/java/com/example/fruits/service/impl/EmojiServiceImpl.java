package com.example.fruits.service.impl;

import com.example.fruits.client.EmojiClient;
import com.example.fruits.model.emoji.FruitEmoji;
import com.example.fruits.service.EmojiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmojiServiceImpl implements EmojiService {
    private final EmojiClient emojiClient;

    @Autowired
    public EmojiServiceImpl(EmojiClient emojiClient) {
        this.emojiClient = emojiClient;
    }

    @Override
    public FruitEmoji getFruitEmoji(String name) {
        log.info("Going to get emoji from emoji-ws by name {}", name);

        return emojiClient.getEmojiByName(name).getBody();
    }

    @Override
    public Map<String, String> getEmojiMap() {
        log.info("Going to get emojis from emoji-ws");

        var fruitEmojis = emojiClient.findAll().getBody();

        if (CollectionUtils.isEmpty(fruitEmojis)) {
            return Collections.emptyMap();
        }

        return fruitEmojis.stream()
            .collect(Collectors.toMap(FruitEmoji::getName, FruitEmoji::getEmoji));
    }
}
