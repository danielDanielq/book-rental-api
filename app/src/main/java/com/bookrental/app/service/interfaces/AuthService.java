package com.bookrental.app.service.interfaces;

import com.bookrental.app.dto.userdto.CreateUserRequest;

public interface AuthService {
    void registerUser(CreateUserRequest createUserRequest);
}
