package com.bookrental.app.service;

import com.bookrental.app.dto.userdto.CreateUserRequest;
import com.bookrental.app.dto.userdto.UserDetailedResponse;
import com.bookrental.app.entity.User;
import com.bookrental.app.exception.EmailAlreadyInUse;
import com.bookrental.app.mapper.UserMapper;
import com.bookrental.app.repository.UserRepository;
import com.bookrental.app.service.interfaces.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;
    private AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Transactional
    public UserDetailedResponse createUser (CreateUserRequest createUserRequest) {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) { // Note: Fail-Fast
            throw new EmailAlreadyInUse("E-mail already in use.");
        }

        User userToSave = UserMapper.toEntity(createUserRequest);

        User savedUser = userRepository.save(userToSave); // Note: other way around this may result in a bug, because keycloak does not back up when Transactional is running;
        createUserRequest.setId(savedUser.getId());
        authService.registerUser(createUserRequest); // Note: important for keycloak register;

        return UserMapper.toDetailedDTO(savedUser);
    }
}
