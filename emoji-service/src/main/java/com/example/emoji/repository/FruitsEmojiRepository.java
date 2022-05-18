package com.example.emoji.repository;

import com.example.emoji.model.FruitEmoji;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FruitsEmojiRepository extends CrudRepository<FruitEmoji, Long> {
    Optional<FruitEmoji> findByNameIgnoreCase(String name);
}
