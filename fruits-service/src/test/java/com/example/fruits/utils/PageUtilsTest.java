package com.example.fruits.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageUtilsTest {

    @Test
    void givenToPage_whenListAndPageable_thenReturnPage() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        PageRequest pageRequest = PageRequest.of(0, 5);
        PageImpl<Integer> actual = PageUtils.toPage(list, pageRequest);

        assertEquals(2, actual.getTotalPages());
        assertEquals(10, actual.getTotalElements());
        assertEquals(list.subList(0, 5), actual.getContent());
    }

    @Test
    void givenToPage_whenEmptyList_thenReturnPage() {
        List<Integer> list = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 5);
        PageImpl<Integer> actual = PageUtils.toPage(list, pageRequest);

        assertEquals(0, actual.getTotalPages());
        assertEquals(0, actual.getTotalElements());
        assertTrue(actual.getContent().isEmpty());
    }
}
