package com.bookrental.app.controllers;

import com.bookrental.app.dtos.user.CreateUser;
import com.bookrental.app.dtos.user.UserWithAddress;
import com.bookrental.app.mappers.UserMapper;
import com.bookrental.app.services.implementations.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserWithAddress> create(@Valid @RequestBody CreateUser user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toUserWithAddress(
                                userService.save(
                                        UserMapper.toUser(user), user.getPassword()
                                )
                        )
                );
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserWithAddress> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserMapper.toUserWithAddress(
                                userService.getUserById(userId)
                        )
                );
    }

}
