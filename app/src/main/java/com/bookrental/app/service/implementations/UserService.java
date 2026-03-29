package com.bookrental.app.services.implementations;

import com.bookrental.app.dtos.AccountRegistration;
import com.bookrental.app.entities.User;
import com.bookrental.app.mappers.UserMapper;
import com.bookrental.app.repositories.UserRepository;
import com.bookrental.app.services.interfaces.IdentityProviderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final IdentityProviderService identityProviderService;

    public UserService(UserRepository userRepository, IdentityProviderService identityProviderService) {
        this.userRepository = userRepository;
        this.identityProviderService = identityProviderService;
    }

    @Transactional
    public User save(User user,String password) {
        user.getAddress().setUser(user);
        User userSaved = userRepository.save(user);
        AccountRegistration accountRegistration = UserMapper.toAccountRegistration(userSaved,password);
        identityProviderService.create(accountRegistration);
        return userSaved;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
    }


}
