package com.example.user.security;

import com.example.user.model.user.LoginRequestModel;
import com.example.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;

    private final long jwtExpiration;
    private final String jwtSecret;

    @Autowired
    public AuthenticationFilter(UserService userService,
                                AuthenticationManager authenticationManager,
                                long jwtExpiration,
                                String jwtSecret) {
        this.userService = userService;
        this.jwtExpiration = jwtExpiration;
        this.jwtSecret = jwtSecret;
        super.setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
        throws AuthenticationException {
        var credentials = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        var userName = ((User) auth.getPrincipal()).getUsername();
        var userDetails = userService.getUserDetailsByEmail(userName);

        String jwt = Jwts.builder()
            .setSubject(userDetails.getUserId())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        res.addHeader("jwt", jwt);
        res.addHeader("userId", userDetails.getUserId());

        log.info("User {} successfully logged in", userName);
    }
}
