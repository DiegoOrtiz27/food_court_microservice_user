package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.exception.*;
import com.foodquart.microserviceuser.domain.util.Role;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase createUserUseCase;

    private UserModel validOwnerUser;
    private UserModel underageOwnerUser;

    @BeforeEach
    void setUp() {
        validOwnerUser = new UserModel();
        validOwnerUser.setFirstName("Jane");
        validOwnerUser.setLastName("Doe");
        validOwnerUser.setDocumentId("12345678");
        validOwnerUser.setPhone("+573001112285");
        validOwnerUser.setBirthDate(LocalDate.now().minusYears(20));
        validOwnerUser.setEmail("jane.doe@example.com");
        validOwnerUser.setPassword("secret123");
        validOwnerUser.setRole(Role.OWNER);

        underageOwnerUser = new UserModel();
        underageOwnerUser.setFirstName("Kid");
        underageOwnerUser.setLastName("User");
        underageOwnerUser.setDocumentId("87654321");
        underageOwnerUser.setPhone("+573001112286");
        underageOwnerUser.setBirthDate(LocalDate.now().minusYears(17));
        underageOwnerUser.setEmail("kid.user@example.com");
        underageOwnerUser.setPassword("secret123");
        underageOwnerUser.setRole(Role.OWNER);
    }

    @Test
    @DisplayName("Should throw exception if required fields are missing")
    void shouldThrowWhenRequiredFieldsMissing() {
        UserModel user = new UserModel(); // empty

        RequiredFieldException ex = assertThrows(RequiredFieldException.class,
                () -> createUserUseCase.saveUser(user));
        assertTrue(ex.getMessage().contains("Name"));
    }

    @Test
    @DisplayName("Should throw exception when email is invalid")
    void shouldThrowWhenEmailInvalid() {
        validOwnerUser.setEmail("invalid-email");

        assertThrows(InvalidEmailFormatException.class,
                () -> createUserUseCase.saveUser(validOwnerUser));
    }

    @Test
    @DisplayName("Should throw exception when phone is invalid")
    void shouldThrowWhenPhoneInvalid() {
        validOwnerUser.setPhone("invalid");

        assertThrows(InvalidPhoneNumberException.class,
                () -> createUserUseCase.saveUser(validOwnerUser));
    }

    @Test
    @DisplayName("Should throw exception when document is not numeric")
    void shouldThrowWhenDocumentInvalid() {
        validOwnerUser.setDocumentId("ABC123");

        assertThrows(InvalidDocumentIdException.class,
                () -> createUserUseCase.saveUser(validOwnerUser));
    }

    @Test
    @DisplayName("Should throw exception if email already exists")
    void shouldThrowWhenEmailExists() {
        when(userPersistencePort.existsByEmail(validOwnerUser.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> createUserUseCase.saveUser(validOwnerUser));

        verify(userPersistencePort).existsByEmail(validOwnerUser.getEmail());
    }

    @Test
    @DisplayName("Should throw exception if document already exists")
    void shouldThrowWhenDocumentIdExists() {
        when(userPersistencePort.existsByEmail(validOwnerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentId(validOwnerUser.getDocumentId())).thenReturn(true);

        assertThrows(DocumentIdAlreadyExistsException.class,
                () -> createUserUseCase.saveUser(validOwnerUser));
    }

    @Test
    @DisplayName("Should throw exception when user is OWNER and underage")
    void shouldThrowWhenUserIsUnderage() {
        assertThrows(UserNotAdultException.class,
                () -> createUserUseCase.saveUser(underageOwnerUser));
    }

    @Test
    @DisplayName("Should save user successfully when all validations pass")
    void shouldSaveUserSuccessfully() {
        when(userPersistencePort.existsByEmail(validOwnerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentId(validOwnerUser.getDocumentId())).thenReturn(false);
        when(passwordEncoderPort.encode("secret123")).thenReturn("encrypted");

        when(userPersistencePort.saveUser(any(UserModel.class))).thenAnswer(invocation -> {
            UserModel user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        createUserUseCase.saveUser(validOwnerUser);

        InOrder inOrder = inOrder(userPersistencePort, passwordEncoderPort);
        inOrder.verify(userPersistencePort).existsByEmail(validOwnerUser.getEmail());
        inOrder.verify(userPersistencePort).existsByDocumentId(validOwnerUser.getDocumentId());
        inOrder.verify(passwordEncoderPort).encode("secret123");
        inOrder.verify(userPersistencePort).saveUser(validOwnerUser);

        assertEquals("encrypted", validOwnerUser.getPassword());
    }
}