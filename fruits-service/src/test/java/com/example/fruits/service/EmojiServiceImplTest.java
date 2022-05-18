package com.example.fruits.service;

import com.example.fruits.client.EmojiClient;
import com.example.fruits.model.emoji.FruitEmoji;
import com.example.fruits.service.impl.EmojiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmojiServiceImplTest {
    private EmojiService emojiService;
    @Mock
    private EmojiClient emojiClient;

    @BeforeEach
    void setUp() {
        emojiService = new EmojiServiceImpl(emojiClient);
    }

    @Test
    void getEmojiMap_whenEmojiList_thenReturnEmojiMap() {
        var emojiList = getEmojiList();
        var entity = new ResponseEntity<>(emojiList, HttpStatus.OK);

        when(emojiClient.findAll())
            .thenReturn(entity);

        var actual = emojiService.getEmojiMap();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertTrue(actual.containsKey(emojiList.get(0).getName()));
        assertTrue(actual.containsKey(emojiList.get(1).getName()));
    }

    @Test
    void getEmojiMap_whenEmptyList_thenReturnEmptyMap() {
        List<FruitEmoji> emojiList = new ArrayList<>();
        var entity = new ResponseEntity<>(emojiList, HttpStatus.OK);

        when(emojiClient.findAll())
            .thenReturn(entity);

        var actual = emojiService.getEmojiMap();

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    private List<FruitEmoji> getEmojiList() {
        var fruitEmoji1 = FruitEmoji.builder()
            .id(1)
            .name("fruit_1")
            .emoji("emoji_1")
            .build();

        var fruitEmoji2 = FruitEmoji.builder()
            .id(2)
            .name("fruit_2")
            .emoji("emoji_2")
            .build();

        return Arrays.asList(fruitEmoji1, fruitEmoji2);
    }
}
