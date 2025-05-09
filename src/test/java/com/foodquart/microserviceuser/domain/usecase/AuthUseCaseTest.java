package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.exception.DomainException;
import com.foodquart.microserviceuser.domain.model.AuthModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IJwtProviderPort;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.util.Role;
import com.foodquart.microserviceuser.domain.util.UserMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @Mock
    private IJwtProviderPort jwtProviderPort;

    @InjectMocks
    private AuthUseCase authUseCase;

    private UserModel validUser;
    private AuthModel validAuth;
    private AuthModel invalidPasswordAuth;
    private AuthModel nonExistentUserAuth;

    @BeforeEach
    void setup() {
        validUser = new UserModel();
        validUser.setId(1L);
        validUser.setFirstName("John");
        validUser.setLastName("Doe");
        validUser.setEmail("john@example.com");
        validUser.setPassword("encodedPassword");
        validUser.setRole(Role.OWNER);
        validUser.setBirthDate(LocalDate.now().minusYears(25));

        validAuth = new AuthModel("john@example.com", "correctPassword");
        invalidPasswordAuth = new AuthModel("john@example.com", "wrongPassword");
        nonExistentUserAuth = new AuthModel("nonexistent@example.com", "anyPassword");
    }

    @Test
    @DisplayName("Should return token when credentials are valid")
    void shouldReturnTokenWhenCredentialsValid() {
        when(userPersistencePort.findByEmail(validAuth.getEmail()))
                .thenReturn(Optional.of(validUser));
        when(passwordEncoderPort.matches(validAuth.getPassword(), validUser.getPassword()))
                .thenReturn(true);
        when(jwtProviderPort.generateToken(validUser)).thenReturn("generated.jwt.token");

        String token = authUseCase.authenticate(validAuth);

        assertNotNull(token);
        assertEquals("generated.jwt.token", token);
        verify(userPersistencePort).findByEmail(validAuth.getEmail());
        verify(passwordEncoderPort).matches(validAuth.getPassword(), validUser.getPassword());
        verify(jwtProviderPort).generateToken(validUser);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowWhenUserNotFound() {
        when(userPersistencePort.findByEmail(nonExistentUserAuth.getEmail()))
                .thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class,
                () -> authUseCase.authenticate(nonExistentUserAuth));

        assertEquals(UserMessages.USER_NOT_FOUND, exception.getMessage());
        verify(userPersistencePort).findByEmail(nonExistentUserAuth.getEmail());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(jwtProviderPort, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should throw exception when password is incorrect")
    void shouldThrowWhenPasswordIncorrect() {
        when(userPersistencePort.findByEmail(validAuth.getEmail()))
                .thenReturn(Optional.of(validUser));
        when(passwordEncoderPort.matches(invalidPasswordAuth.getPassword(), validUser.getPassword()))
                .thenReturn(false);

        DomainException exception = assertThrows(DomainException.class,
                () -> authUseCase.authenticate(invalidPasswordAuth));

        assertEquals(UserMessages.INVALID_CREDENTIALS, exception.getMessage());
        verify(userPersistencePort).findByEmail(validAuth.getEmail());
        verify(passwordEncoderPort).matches(invalidPasswordAuth.getPassword(), validUser.getPassword());
        verify(jwtProviderPort, never()).generateToken(any());
    }
}