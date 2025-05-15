package com.foodquart.microserviceuser.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthModelTest {

    @Test
    void shouldCreateAuthModelWithAllArgsConstructor() {
        String email = "test@example.com";
        String password = "securePassword";
        AuthModel authModel = new AuthModel(email, password);

        assertEquals(email, authModel.getEmail());
        assertEquals(password, authModel.getPassword());
    }

    @Test
    void shouldCreateAuthModelWithNoArgsConstructorAndSetters() {
        AuthModel authModel = new AuthModel();
        assertNull(authModel.getEmail());
        assertNull(authModel.getPassword());

        String email = "another@example.com";
        String password = "anotherPassword";
        authModel.setEmail(email);
        authModel.setPassword(password);

        assertEquals(email, authModel.getEmail());
        assertEquals(password, authModel.getPassword());
    }

    @Test
    void shouldSetAndGetEmailCorrectly() {
        AuthModel authModel = new AuthModel();
        String email = "update@example.com";
        authModel.setEmail(email);
        assertEquals(email, authModel.getEmail());
    }

    @Test
    void shouldSetAndGetPasswordCorrectly() {
        AuthModel authModel = new AuthModel();
        String password = "newSecurePassword";
        authModel.setPassword(password);
        assertEquals(password, authModel.getPassword());
    }

}