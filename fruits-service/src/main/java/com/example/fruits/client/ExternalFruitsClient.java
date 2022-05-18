package com.example.fruits.client;

import com.example.fruits.dto.FruitInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ExternalFruitsClient {
    private static final String FRUITS_ALL_PATH = "/fruit/all";
    private static final String FRUIT_BY_NAME_PATH = "/fruit/{name}";

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public ExternalFruitsClient(RestTemplate restTemplate,
                                @Value("${client.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<FruitInfoDto> getAllFruits() {
        ResponseEntity<List<FruitInfoDto>> responseEntity =
            restTemplate.exchange(baseUrl + FRUITS_ALL_PATH,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody();
    }

    public FruitInfoDto getFruit(String name) {
        ResponseEntity<FruitInfoDto> entity =
            restTemplate.getForEntity(baseUrl + FRUIT_BY_NAME_PATH, FruitInfoDto.class, name);

        return entity.getBody();
    }
}
