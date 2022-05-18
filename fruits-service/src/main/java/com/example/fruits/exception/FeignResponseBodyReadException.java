package com.example.fruits.exception;

import feign.FeignException;

public class FeignResponseBodyReadException extends FeignException {

    public FeignResponseBodyReadException(int status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
