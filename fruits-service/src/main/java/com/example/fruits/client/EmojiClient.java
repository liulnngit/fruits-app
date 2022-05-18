package com.example.fruits.client;

import com.example.fruits.model.emoji.FruitEmoji;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "emoji-ws")
public interface EmojiClient {
    @GetMapping("/api/v1/emojis/{name}")
    public ResponseEntity<FruitEmoji> getEmojiByName(@PathVariable String name);

    @GetMapping("/api/v1/emojis")
    public ResponseEntity<List<FruitEmoji>> findAll();
}
