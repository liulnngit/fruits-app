package com.example.emoji.controller;

import com.example.emoji.model.FruitEmoji;
import com.example.emoji.service.impl.FruitsEmojiService;
import com.example.emoji.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emojis")
public class EmojiController {
    private final FruitsEmojiService fruitsEmojiService;

    @Autowired
    public EmojiController(FruitsEmojiService fruitsEmojiService) {
        this.fruitsEmojiService = fruitsEmojiService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FruitEmoji>> getAll() {

        return HttpUtils.ok(fruitsEmojiService.findAll());
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FruitEmoji> getEmojiByName(@PathVariable String name) {

        return HttpUtils.ok(fruitsEmojiService.findEmoji(name));
    }
}
