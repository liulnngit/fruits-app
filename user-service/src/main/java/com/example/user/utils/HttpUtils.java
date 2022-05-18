package com.example.user.utils;

import com.example.user.dto.DataResultObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.NONE)
public class HttpUtils {

    public static <T> ResponseEntity<DataResultObject<T>> ok(T data) {
        return withStatus(data, HttpStatus.OK);
    }

    public static ResponseEntity<DataResultObject<Void>> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static <T> ResponseEntity<DataResultObject<T>> withStatus(T data, HttpStatus status) {
        return new ResponseEntity<>(new DataResultObject<>(data), status);
    }

}