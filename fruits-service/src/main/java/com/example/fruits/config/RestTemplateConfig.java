package com.example.fruits.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("${client.timeout.connect}")
    private int connectTimeout;
    @Value("${client.timeout.read}")
    private int readTimeout;

    @Bean
    RestTemplate restTemplateWithErrorHandler() {
        return new RestTemplateBuilder()
            .requestFactory(this::getClientHttpRequestFactory)
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        var config = RequestConfig.custom()
            .setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectTimeout)
            .setSocketTimeout(connectTimeout)
            .build();

        var client = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(config)
            .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
