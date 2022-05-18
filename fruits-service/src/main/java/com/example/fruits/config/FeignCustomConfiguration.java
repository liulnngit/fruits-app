package com.example.fruits.config;

import com.example.fruits.exception.DefaultErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCustomConfiguration {

    @Bean
    ErrorDecoder errorDecoder() {
        return new DefaultErrorDecoder();
    }
}
