package com.example.fruits.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
public class DefaultErrorDecoder implements ErrorDecoder {
    private final Default defaultDecoder;

    public DefaultErrorDecoder() {
        defaultDecoder = new Default();
    }

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 400:
                try {
                    Map<String, String> map = Collections.emptyMap();
                    if (response.body() != null) {
                        String body = StreamUtils.copyToString(response.body().asInputStream(), Charset.defaultCharset());
                        ObjectMapper mapper = new ObjectMapper();
                        map = mapper.readValue(body, new TypeReference<HashMap<String, String>>() {
                        });
                    }
                    return new BadRequestException(map);
                } catch (IOException e) {
                    throw new FeignResponseBodyReadException(response.status(), "Error reading body", e);
                }
            case 404:
                return new NoSuchElementException();
            default:
                return defaultDecoder.decode(s, response);
        }
    }

}
