package com.example.fruits.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;

import java.util.Map;

public class BadRequestException extends HystrixBadRequestException {
    @Getter
    private final Map<String, String> body;

    public BadRequestException(Map<String, String> body) {
        super("Bad request: " + body.toString());
        this.body = body;
    }
}
