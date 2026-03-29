package com.bookrental.app.controllers;

import com.bookrental.app.dtos.Authentication;
import com.bookrental.app.services.interfaces.IdentityProviderService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("authentication")
class AuthenticatioController {

    private final IdentityProviderService identityProviderService;

    public AuthenticatioController(IdentityProviderService identityProviderService) {
        this.identityProviderService = identityProviderService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(identityProviderService.generateToken(authentication));
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK).body(identityProviderService.refreshToken(refreshToken));
    }

    @GetMapping("whoami")
    public ResponseEntity<Map<String,Object>> whoami(Principal principal) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("name", principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(claim);
    }
}
