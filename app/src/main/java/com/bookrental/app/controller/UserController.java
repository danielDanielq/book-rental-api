package com.bookrental.app.controller;

import com.bookrental.app.dto.userdto.CreateUserRequest;
import com.bookrental.app.dto.userdto.UserDetailedResponse;
import com.bookrental.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users") // Note: Becarefull with application.properties while using server.servlet.contextPath=/api;
public class UserController { // Note: The controller should do little stuff and be as simple;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailedResponse> register(@Valid @RequestBody CreateUserRequest createUserRequest) { // Note: The @Valid adnotation is used to check the internal adnotations explicitly put by the programer, otherwise they will pass;
        UserDetailedResponse userDetailedResponse = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailedResponse);
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(@AuthenticationPrincipal Jwt jwt) { // Note: when using token Bearer a hidden filter from Security (BearerTokenAuthenticationFilter) creates a jwt object in RAM memory. This jwt = decoded token json;
        Map<String, Object> claims = new HashMap<>();

        Object userId = jwt.getClaim("user_id"); // Note: "user_id" = Token Claim Name from the Mapper details in keycloak;
        String email = jwt.getClaim("preferred_username");

        claims.put("db_id", userId);
        claims.put("email", email);

        return ResponseEntity.status(200).body(claims);
    }

}
