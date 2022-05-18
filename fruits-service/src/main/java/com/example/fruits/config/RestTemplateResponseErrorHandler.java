package com.example.fruits.config;

import com.example.fruits.exception.FruitClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return httpResponse.getStatusCode().series() == CLIENT_ERROR
            || httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        String responseAsString = getResponseMessage(httpResponse);
        log.error("ResponseBody: {}", responseAsString);

        throw new FruitClientException(httpResponse.getStatusCode(), responseAsString);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse httpResponse) throws IOException {
        String responseAsString = getResponseMessage(httpResponse);
        log.error("URL: {}, HttpMethod: {}, ResponseBody: {}", url, method, responseAsString);

        if (HttpStatus.NOT_FOUND == httpResponse.getStatusCode()) {
            throw new NoSuchElementException(responseAsString);
        }

        throw new FruitClientException(httpResponse.getStatusCode(), responseAsString);
    }

    private String getResponseMessage(ClientHttpResponse httpResponse) {
        String httpBodyResponse = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getBody()))) {
            httpBodyResponse = reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            log.error("Could not get client response body: " + e.getLocalizedMessage());
        }

        return httpBodyResponse;
    }
}
