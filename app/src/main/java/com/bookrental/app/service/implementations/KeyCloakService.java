package com.bookrental.app.services.implementations;

import com.bookrental.app.dtos.AccountRegistration;
import com.bookrental.app.dtos.Authentication;
import com.bookrental.app.exception.AccountAlreadyExistsException;
import com.bookrental.app.services.interfaces.IdentityProviderService;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.List;

@Service
public class KeyCloakService implements IdentityProviderService {
    @Value("${spring.security.oauth2.client.provider.book-rental-auth.issuer-uri}")
    private String issuerUri;

    @Value("${auth.oauth2.server-url}")
    private String serverUrl;

    @Value("${spring.security.oauth2.client.provider.book-rental-auth.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.registration.my-login-client.client-id}")
    private String clientId;

    @Value(("${spring.security.oauth2.client.registration.my-login-client.client-secret}"))
    private String clientSecret;

    @Value("${auth.oauth2.realm}")
    private String realm;

    @Value("${auth.oauth2.admin.username}")
    private String adminUsername;

    @Value("${auth.oauth2.admin.password}")
    private String adminPassword;

    @Value("${auth.oauth2.admin.client-id}")
    private String adminClientId;

    @Value("${auth.oauth2.admin.realm}")
    private String adminRealm;

    @Override
    public Map<String, Object> generateToken(Authentication authentication) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = prepareRequest(authentication);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return restTemplate.postForObject(tokenUri, request, Map.class);
    }

    @Override
    public Map<String, Object> refreshToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = prepareRequest(refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return restTemplate.postForObject(tokenUri, request, Map.class);
    }

    @Override
    public void create(AccountRegistration accountRegistration) {
        Keycloak adminClient = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(adminRealm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(adminClientId)
                .username(adminUsername)
                .password(adminPassword)
                .build();

        UserRepresentation user = createUser(accountRegistration);
        Response response = adminClient.realm(realm).users().create(user);

        if(response.getStatus() == HttpStatus.CONFLICT.value()) {
            throw new AccountAlreadyExistsException("Account already exists");
        }

        String userId = CreatedResponseUtil.getCreatedId(response);

        RoleRepresentation role = adminClient.realm(realm).roles().get(accountRegistration.getRole().getType()).toRepresentation();
        adminClient.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role));

        response.close();
    }

    private UserRepresentation createUser(AccountRegistration accountRegistration) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setEmail(accountRegistration.getEmail());
        user.setFirstName(accountRegistration.getFirstname());
        user.setLastName(accountRegistration.getLastname());
        user.setUsername(accountRegistration.getFirstname());

        setPassword(user, accountRegistration.getPassword());

        return user;
    }

    private void setPassword(UserRepresentation user, String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        user.setCredentials(Collections.singletonList(passwordCredentials));
    }

    private MultiValueMap<String, String> prepareRequest(Authentication authentication) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("username", authentication.getEmail());
        map.add("password", authentication.getPassword());
        return map;
    }

    private MultiValueMap<String, String> prepareRequest(String refreshToken) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", refreshToken);
        return map;
    }

}
