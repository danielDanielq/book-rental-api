package com.bookrental.app.service;

import com.bookrental.app.dto.userdto.CreateUserRequest;
import com.bookrental.app.enums.Role;
import com.bookrental.app.exception.AccountAlreadyExists;
import com.bookrental.app.service.interfaces.AuthService;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeyCloakService implements AuthService {

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

    private Keycloak keycloakAdmin;

    @PostConstruct
    public void init() {
        keycloakAdmin = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(adminRealm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(adminClientId)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    @Override
    public void registerUser(CreateUserRequest createUserRequest) {
        UserRepresentation userRepresentation = new UserRepresentation(); // Note: Keycloak's internal DTO's called Representations;
        userRepresentation.setEmail(createUserRequest.getEmail());
        userRepresentation.setUsername(createUserRequest.getEmail()); // Note: Keycloak looks for a unique username, the e-mail fits perfectly here;
        userRepresentation.setFirstName(createUserRequest.getFirstName());
        userRepresentation.setLastName(createUserRequest.getLastName());
        userRepresentation.singleAttribute("id", String.valueOf(createUserRequest.getId())); // Note: "id" comes from the User Attribute in the Mapper details;
        //userRepresentation.setId(String.valueOf(createUserRequest.getId())); Note: this will set the UUID not a personalized atribute;
        userRepresentation.setEnabled(true); // Note: Activate the account otherwise it will be blocked and need the admin to unblock on keycloak UI;

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation(); // Note: Create the password/key(credetials);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(createUserRequest.getPassword());
        credentialRepresentation.setTemporary(false); // Note: Otherwise the user will be forced to change password when logging in;

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation)); // Note: KeyCloak allows for a list of credentials (OTP, phone codes);

        Response response = keycloakAdmin.realm(realm).users().create(userRepresentation); // Note: KeyCloak comunication with the backend is actually an HTTP client under the hood. Sending the POST request and the response is saved here;

        if (response.getStatus() != 201) { // Note: for e-mail already in use there is 409 (Conflict) error type;
            throw new AccountAlreadyExists("?Account already registered?: " + response.getStatus());
        }

        String userId = CreatedResponseUtil.getCreatedId(response); // Note: the response (from keycloak) will have the HTTP header containing a UUID (keycloak id of the user);
        RoleRepresentation role = keycloakAdmin.realm(realm).roles().get(Role.CLIENT.getType()).toRepresentation(); // Note: get the role mapped to DTO specific for keycloak (Representation). We force the Role to CLIENT for security reasons;
        keycloakAdmin.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role)); // Note: set the role in the keycloak using the UUID;

        response.close(); // Note: close the HTTP conection;
    }
}
