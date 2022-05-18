package com.example.user.security;

import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    public static final String HAS_IP_ADDRESS_127_0_0_0_16 = "hasIpAddress(\"192.168.0.0/16\") or hasIpAddress(\"127.0.0.0/16\")";
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String loginUrlPath;
    private final long jwtExpiration;
    private final String jwtSecret;

    @Autowired
    public WebSecurity(UserService userService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       @Value("${login.url.path}") String loginUrlPath,
                       @Value("${jwt.expiration}") long jwtExpiration,
                       @Value("${jwt.secret}") String jwtSecret) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginUrlPath = loginUrlPath;
        this.jwtExpiration = jwtExpiration;
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, loginUrlPath).permitAll()//.access(HAS_IP_ADDRESS_127_0_0_0_16)
//            .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()//.access(HAS_IP_ADDRESS_127_0_0_0_16)
//            .antMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()//.access(HAS_IP_ADDRESS_127_0_0_0_16)
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(
            userService,
            authenticationManager(),
            jwtExpiration,
            jwtSecret
        );
        authenticationFilter.setFilterProcessesUrl(loginUrlPath);

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
