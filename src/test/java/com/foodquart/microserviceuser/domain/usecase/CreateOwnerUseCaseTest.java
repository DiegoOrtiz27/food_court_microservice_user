package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.exception.DocumentIdAlreadyExistsException;
import com.foodquart.microserviceuser.domain.exception.EmailAlreadyExistsException;
import com.foodquart.microserviceuser.domain.exception.UserNotAdultException;
import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IOwnerPersistencePort;
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
class CreateOwnerUseCaseTest {

    @Mock IOwnerPersistencePort ownerPersistencePort;
    @Mock IUserPersistencePort userPersistencePort;
    @Mock IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    CreateOwnerUseCase createOwnerUseCase;

    private UserModel underageUser;
    private UserModel adultUser;
    private OwnerModel ownerModel;

    @BeforeEach
    void setup() {
        underageUser = new UserModel();
        underageUser.setFirstName("Jhon");
        underageUser.setLastName("Due");
        underageUser.setEmail("JhonDue@example.com");
        underageUser.setBirthDate(LocalDate.now().minusYears(17));
        underageUser.setPassword("raw");
        underageUser.setDocumentId("10000");
        underageUser.setPhone("+573005698327");

        adultUser = new UserModel();
        adultUser.setFirstName("Lili");
        adultUser.setLastName("Gomez");
        adultUser.setEmail("LiliGomez@example.com");
        adultUser.setBirthDate(LocalDate.now().minusYears(25));
        adultUser.setPassword("raw");
        adultUser.setDocumentId("100001");
        adultUser.setPhone("+573005698326");

        ownerModel = new OwnerModel();
    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException if the email already exists")
    void saveOwnerWhenEmailExistsThenThrows() {
        when(userPersistencePort.existsByEmail(underageUser.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> createOwnerUseCase.saveOwner(ownerModel, underageUser));

        verify(userPersistencePort).existsByEmail(underageUser.getEmail());
        verifyNoMoreInteractions(userPersistencePort, passwordEncoderPort, ownerPersistencePort);
    }

    @Test
    @DisplayName("Should throw - DocumentIdAlreadyExistsException if the documentId already exists")
    void saveOwnerWhenDocumentIdExistsThenThrows() {
        when(userPersistencePort.existsByEmail(underageUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentId(underageUser.getDocumentId())).thenReturn(true);

        assertThrows(DocumentIdAlreadyExistsException.class,
                () -> createOwnerUseCase.saveOwner(ownerModel, underageUser));

        InOrder inOrder = inOrder(userPersistencePort);
        inOrder.verify(userPersistencePort)
                .existsByEmail(underageUser.getEmail());
        inOrder.verify(userPersistencePort)
                .existsByDocumentId(underageUser.getDocumentId());

        verifyNoMoreInteractions(userPersistencePort, passwordEncoderPort, ownerPersistencePort);
    }

    @Test
    @DisplayName("Must throw UserNotAdultException if the user is a minor")
    void saveOwnerWhenUserIsUnderageShouldThrowUserNotAdultException() {
        when(userPersistencePort.existsByEmail(underageUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentId(underageUser.getDocumentId())).thenReturn(false);

        assertThrows(UserNotAdultException.class,
                () -> createOwnerUseCase.saveOwner(ownerModel, underageUser));

        InOrder inOrder = inOrder(userPersistencePort);
        inOrder.verify(userPersistencePort)
                .existsByEmail(underageUser.getEmail());
        inOrder.verify(userPersistencePort)
                .existsByDocumentId(underageUser.getDocumentId());

        verifyNoMoreInteractions(userPersistencePort, passwordEncoderPort, ownerPersistencePort);
    }

    @Test
    @DisplayName("Must successfully save an Owner if the user is of legal age and the email does not exist")
    void saveOwnerWhenValidAdultUserShouldSaveOwnerSuccessfully() {
        when(userPersistencePort.existsByEmail(adultUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentId(adultUser.getDocumentId())).thenReturn(false);
        when(passwordEncoderPort.encode("raw")).thenReturn("ENCODED");
        when(userPersistencePort.saveUser(any(UserModel.class))).thenAnswer(invocation -> {
            UserModel user = invocation.getArgument(0);
            user.setId(99L);
            return user;
        });

        createOwnerUseCase.saveOwner(ownerModel, adultUser);

        InOrder inOrder = inOrder(userPersistencePort, passwordEncoderPort, ownerPersistencePort);
        inOrder.verify(userPersistencePort).existsByEmail(adultUser.getEmail());
        inOrder.verify(userPersistencePort).existsByDocumentId(adultUser.getDocumentId());
        inOrder.verify(passwordEncoderPort).encode("raw");
        inOrder.verify(userPersistencePort).saveUser(adultUser);

        assertEquals(99L, ownerModel.getUser().getId());
        verify(ownerPersistencePort).saveOwner(ownerModel);
    }

}