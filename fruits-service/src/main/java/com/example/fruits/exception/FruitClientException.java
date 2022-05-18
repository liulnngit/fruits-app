package com.example.fruits.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
public class FruitClientException extends IOException {
    private final HttpStatus statusCode;
    private final String error;

    public FruitClientException(HttpStatus statusCode, String error) {
        super(error);
        this.statusCode = statusCode;
        this.error = error;
    }
}
