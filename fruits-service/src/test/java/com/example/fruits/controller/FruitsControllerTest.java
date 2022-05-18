package com.example.fruits.controller;

import com.example.fruits.controller.assembler.FruitsModelAssembler;
import com.example.fruits.model.emoji.FruitWithEmoji;
import com.example.fruits.model.emoji.Nutrition;
import com.example.fruits.service.FruitsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FruitsController.class)
@Import({FruitsModelAssembler.class})
class FruitsControllerTest {
    static final Pageable PAGEABLE = PageRequest.of(0, 2);

    @MockBean
    FruitsService fruitsService;

    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    void getAll_shouldReturnPageWithFruitsList() {
        when(fruitsService.getAllFruitsWithEmojis(any(Pageable.class)))
            .thenReturn(getPage());

        mockMvc.perform(
                get("/api/v1/fruits")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.fruits[0].id", is(1)))
            .andExpect(jsonPath("$._embedded.fruits[0].name", is("fruit_1")))
            .andExpect(jsonPath("$._embedded.fruits[0].emoji", is("emoji_1")))
            .andExpect(jsonPath("$._embedded.fruits[0].nutritions.carbohydrates", is(8.0)))
            .andExpect(jsonPath("$._embedded.fruits[0].nutritions.protein", is(3.1)))
            .andExpect(jsonPath("$._embedded.fruits[0].nutritions.fat", is(9.0)))
            .andExpect(jsonPath("$._embedded.fruits[0].nutritions.calories", is(10)))
            .andExpect(jsonPath("$._embedded.fruits[0].nutritions.sugar", is(5.0)))
            .andExpect(jsonPath("$._embedded.fruits", hasSize(2)))
            .andReturn();
    }

    private Page<FruitWithEmoji> getPage() {
        var emojiList = getFruitWithEmojiList();

        return new PageImpl<>(emojiList, PAGEABLE, 2);
    }

    private List<FruitWithEmoji> getFruitWithEmojiList() {
        var nutrition1 = Nutrition.builder()
            .calories(10)
            .fat(9.0)
            .sugar(5.0)
            .carbohydrates(8.0)
            .protein(3.1)
            .build();

        var fruitEmoji1 = FruitWithEmoji.builder()
            .id(1)
            .name("fruit_1")
            .emoji("emoji_1")
            .nutritions(nutrition1)
            .build();

        var nutrition2 = Nutrition.builder()
            .calories(11)
            .fat(8.0)
            .sugar(5.5)
            .carbohydrates(7.0)
            .protein(3.3)
            .build();

        var fruitEmoji2 = FruitWithEmoji.builder()
            .id(2)
            .name("fruit_2")
            .emoji("emoji_2")
            .nutritions(nutrition2)
            .build();

        return Arrays.asList(fruitEmoji1, fruitEmoji2);
    }
}
