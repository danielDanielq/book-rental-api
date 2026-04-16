package com.bookrental.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Note: Spring will process this class before using the controllers;
@EnableWebSecurity // Note: This is a specific adnotation for Spring Security, instatiating filters(classes) for HTTP security;
public class SecurityConfig {
    @Bean // Note: What do you do when you want to inject an object of a class you didn't write? @Bean solves this problem, stores the return object in IoC container and will use it automatic when needed;
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http // Note: This uses Builder Pattern with functional interfaces (Customizer), that's why the lambda functions;
                .csrf(csrfConfig -> csrfConfig.disable()) // Note: A CSRF token it's a safety implementation for and old arhitecture. As an REST API we use JWT so there is no need for this toker, mandatory disable it;
                .authorizeHttpRequests(auth -> auth // Note: It's a Deny-by-Default security golden rule so we need to change that (register, login and other public documantations endpoints);
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll() // Note: All endpoints on this POST URL will be permited (open door);
                        .anyRequest().authenticated() // Note: Any other different requests that does not match the mapper and URL (the endpoint) will need authentication, meaning a JWT from keycloak;
                )
                .oauth2ResourceServer(oauth2 -> oauth2 // Note: We explicitly specify the route authentication (you can choose HTML login, Basic Auth(User:Password in Header) or OAuth2 Token which uses JWT);
                        .jwt(jwt -> {}) // Note: Automatically using the Keycloak details from application.properties to validate the incoming JWTs with no parsing the JWT explicitly;
                );

        return http.build();
    }
}
