package com.bookrental.app.controller;

import com.bookrental.app.dto.userdto.CreateUserRequest;
import com.bookrental.app.dto.userdto.UserDetailedResponse;
import com.bookrental.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users") // Note: Becarefull with application.properties while using server.servlet.contextPath=/api;
public class UserController { // Note: The controller should do little stuff and be as simple;
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailedResponse> register(@Valid @RequestBody CreateUserRequest createUserRequest) { // Note: The @Valid adnotation is used to check the internal adnotation explicitly put by the programer, otherwise they will pass;
        UserDetailedResponse userDetailedResponse = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailedResponse);
    }


}
