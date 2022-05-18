package com.example.emoji.service;

import com.example.emoji.model.FruitEmoji;
import com.example.emoji.repository.FruitsEmojiRepository;
import com.example.emoji.service.impl.FruitsEmojiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FruitsEmojiServiceImpl implements FruitsEmojiService {
    private final FruitsEmojiRepository repository;

    @Autowired
    public FruitsEmojiServiceImpl(FruitsEmojiRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FruitEmoji> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public FruitEmoji findEmoji(String name) {
        return repository.findByNameIgnoreCase(name)
            .orElseThrow(() -> new NoSuchElementException("Emoji not found"));
    }
}
