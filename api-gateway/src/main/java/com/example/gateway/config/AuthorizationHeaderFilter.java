package com.example.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    public static final String NO_AUTHORIZATION_HEADER_ERROR = "No authorization header";
    public static final String JWT_IS_NOT_VALID_ERROR = "JWT is not valid";
    public static final String BEARER = "Bearer";

    private final String jwtSecret;

    @Autowired
    public AuthorizationHeaderFilter(@Value("${jwt.secret}") String jwtSecret) {
        super(Config.class);
        this.jwtSecret = jwtSecret;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, NO_AUTHORIZATION_HEADER_ERROR, HttpStatus.UNAUTHORIZED);
            }

            var authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            var jwt = authorizationHeader.replace(BEARER, "");

            if (!JwtUtils.isTokenValid(jwt, jwtSecret)) {
                return onError(exchange, JWT_IS_NOT_VALID_ERROR, HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        var response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        var bytes = error.getBytes(StandardCharsets.UTF_8);
        var buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {
        //put configuration properties here
    }

}
