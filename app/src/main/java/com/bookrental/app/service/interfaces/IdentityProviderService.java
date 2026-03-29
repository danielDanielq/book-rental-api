package com.bookrental.app.services.interfaces;

import com.bookrental.app.dtos.AccountRegistration;
import com.bookrental.app.dtos.Authentication;

import java.util.Map;

public interface IdentityProviderService {
    Map<String, Object> generateToken(Authentication authentication);
    Map<String, Object> refreshToken(String refreshToken);
    void create(AccountRegistration accountRegistration);
}
