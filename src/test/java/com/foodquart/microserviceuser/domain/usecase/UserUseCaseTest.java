package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.exception.DomainException;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.util.Role;
import com.foodquart.microserviceuser.domain.util.UserMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel validOwner;
    private UserModel validEmployee;
    private UserModel validCustomer;

    @BeforeEach
    void setup() {
        validOwner = createUser(
                "John", "Doe", "12345678", "+573001234567",
                "john@example.com", "password123", Role.OWNER, LocalDate.now().minusYears(20)
        );

        validEmployee = createUser(
                "Jane", "Smith", "87654321", "+573008765432",
                "jane@example.com", "password456", Role.EMPLOYEE, null
        );

        validCustomer = createUser(
                "Bob", "Johnson", "11223344", "+573009876543",
                "bob@example.com", "password789", Role.CUSTOMER, null
        );
    }

    private UserModel createUser(String firstName, String lastName, String documentId,
                                 String phone, String email, String password,
                                 Role role, LocalDate birthDate) {
        UserModel user = new UserModel();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDocumentId(documentId);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setBirthDate(birthDate);
        return user;
    }

    @Nested
    @DisplayName("Save User Tests")
    class SaveUserTests {

        @Test
        @DisplayName("Should save owner successfully with valid data")
        void shouldSaveOwnerSuccessfully() {
            when(userPersistencePort.existsByEmail(validOwner.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(validOwner.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(validOwner.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(validOwner);

            UserModel result = userUseCase.saveUser(validOwner);

            assertNotNull(result);
            assertEquals("encryptedPassword", result.getPassword());
            verify(userPersistencePort).saveUser(any(UserModel.class));
        }

        @Test
        @DisplayName("Should save employee successfully with valid data")
        void shouldSaveEmployeeSuccessfully() {
            when(userPersistencePort.existsByEmail(validEmployee.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(validEmployee.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(validEmployee.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(validEmployee);

            UserModel result = userUseCase.saveUser(validEmployee);

            assertNotNull(result);
            verify(userPersistencePort).saveUser(any(UserModel.class));
        }

        @Test
        @DisplayName("Should save customer successfully with valid data")
        void shouldSaveCustomerSuccessfully() {
            when(userPersistencePort.existsByEmail(validCustomer.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(validCustomer.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(validCustomer.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(validCustomer);

            UserModel result = userUseCase.saveUser(validCustomer);

            assertNotNull(result);
            assertEquals(Role.CUSTOMER, result.getRole());
            assertEquals("encryptedPassword", result.getPassword());
            verify(userPersistencePort).saveUser(any(UserModel.class));
        }

        @Test
        @DisplayName("Should throw exception when email is already used")
        void shouldThrowWhenEmailExists() {
            when(userPersistencePort.existsByEmail(validOwner.getEmail())).thenReturn(true);

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.saveUser(validOwner));

            assertEquals(String.format(UserMessages.EMAIL_ALREADY_USED, validOwner.getEmail()),
                    exception.getMessage());
            verify(userPersistencePort, never()).saveUser(any());
        }

        @Test
        @DisplayName("Should throw exception when document ID is already used")
        void shouldThrowWhenDocumentIdExists() {
            when(userPersistencePort.existsByEmail(validOwner.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(validOwner.getDocumentId())).thenReturn(true);

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.saveUser(validOwner));

            assertEquals(String.format(UserMessages.DOCUMENT_ID_ALREADY_USED, validOwner.getDocumentId()),
                    exception.getMessage());
            verify(userPersistencePort, never()).saveUser(any());
        }

        @Test
        @DisplayName("Should throw exception when owner is underage")
        void shouldThrowWhenOwnerUnderage() {
            UserModel underageOwner = createUser(
                    "Young", "Owner", "99999999", "+573001111111",
                    "young@example.com", "password123", Role.OWNER, LocalDate.now().minusYears(17)
            );

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.saveUser(underageOwner));

            assertEquals(String.format(UserMessages.USER_NOT_ADULT, "Young", "Owner"),
                    exception.getMessage());
            verify(userPersistencePort, never()).existsByEmail(any());
            verify(userPersistencePort, never()).existsByDocumentId(any());
            verify(userPersistencePort, never()).saveUser(any());
        }

        @Test
        @DisplayName("Should throw exception when phone number is invalid")
        void shouldThrowWhenPhoneInvalid() {
            UserModel invalidPhoneUser = createUser(
                    "Test", "User", "11111111", "3001234567", // Falta el +
                    "test@example.com", "password123", Role.OWNER, LocalDate.now().minusYears(20)
            );

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.saveUser(invalidPhoneUser));

            assertEquals(String.format(UserMessages.INVALID_PHONE_NUMBER, "3001234567"),
                    exception.getMessage());
            verify(userPersistencePort, never()).saveUser(any());
        }

        @Test
        @DisplayName("Should throw exception when email is invalid")
        void shouldThrowWhenEmailInvalid() {
            UserModel invalidEmailUser = createUser(
                    "Test", "User", "11111111", "+573001234567",
                    "invalid-email", "password123", Role.OWNER, LocalDate.now().minusYears(20)
            );

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.saveUser(invalidEmailUser));

            assertEquals(String.format(UserMessages.INVALID_EMAIL, "invalid-email"),
                    exception.getMessage());
            verify(userPersistencePort, never()).saveUser(any());
        }

        @Test
        @DisplayName("Should throw exception when document ID is not numeric")
        void shouldThrowWhenDocumentIdNotNumeric() {
            UserModel invalidDocUser = createUser(
                    "Test", "User", "ABC123", "+573001234567",
                    "test@example.com", "password123", Role.OWNER, LocalDate.now().minusYears(20)
            );

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.saveUser(invalidDocUser));

            assertEquals(String.format(UserMessages.INVALID_DOCUMENT_ID, "ABC123"),
                    exception.getMessage());
            verify(userPersistencePort, never()).saveUser(any());
        }


    }

    @Nested
    @DisplayName("Get User Info Tests")
    class GetUserInfoTests {

        @Test
        @DisplayName("Should return user when exists")
        void shouldReturnUserWhenExists() {
            when(userPersistencePort.findById(1L)).thenReturn(Optional.of(validOwner));

            UserModel result = userUseCase.getUserInfo(1L);

            assertNotNull(result);
            assertEquals(validOwner.getEmail(), result.getEmail());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void shouldThrowWhenUserNotFound() {
            when(userPersistencePort.findById(999L)).thenReturn(Optional.empty());

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.getUserInfo(999L));

            assertEquals(UserMessages.USER_NOT_FOUND, exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Create User with Specific Role Tests")
    class CreateUserWithRoleTests {

        @Test
        @DisplayName("Should create owner with correct role")
        void shouldCreateOwnerWithCorrectRole() {
            UserModel inputUser = createUser(
                    "New", "Owner", "12345678", "+573001234567",
                    "new@example.com", "password123", null, LocalDate.now().minusYears(20)
            );

            UserModel expectedUser = createUser(
                    "New", "Owner", "12345678", "+573001234567",
                    "new@example.com", "password123", Role.OWNER, LocalDate.now().minusYears(20)
            );

            when(userPersistencePort.existsByEmail(inputUser.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(inputUser.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(inputUser.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(expectedUser);

            UserModel result = userUseCase.createOwner(inputUser);

            assertNotNull(result);
            assertEquals(Role.OWNER, result.getRole());
            verify(userPersistencePort).saveUser(any(UserModel.class));
        }

        @Test
        @DisplayName("Should create employee with correct role")
        void shouldCreateEmployeeWithCorrectRole() {
            UserModel inputUser = createUser(
                    "New", "Employee", "87654321", "+573008765432",
                    "employee@example.com", "password456", null, null
            );

            UserModel expectedUser = createUser(
                    "New", "Employee", "87654321", "+573008765432",
                    "employee@example.com", "password456", Role.EMPLOYEE, null
            );

            when(userPersistencePort.existsByEmail(inputUser.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(inputUser.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(inputUser.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(expectedUser);

            UserModel result = userUseCase.createEmployee(inputUser);

            assertNotNull(result);
            assertEquals(Role.EMPLOYEE, result.getRole());
            verify(userPersistencePort).saveUser(any(UserModel.class));
        }

        @Test
        @DisplayName("Should create customer with correct role")
        void shouldCreateCustomerWithCorrectRole() {
            UserModel inputUser = createUser(
                    "New", "Customer", "11223344", "+573009876543",
                    "customer@example.com", "password789", null, null
            );

            UserModel expectedUser = createUser(
                    "New", "Customer", "11223344", "+573009876543",
                    "customer@example.com", "password789", Role.CUSTOMER, null
            );

            when(userPersistencePort.existsByEmail(inputUser.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(inputUser.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(inputUser.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(expectedUser);

            UserModel result = userUseCase.createCustomer(inputUser);

            assertNotNull(result);
            assertEquals(Role.CUSTOMER, result.getRole());
            verify(userPersistencePort).saveUser(any(UserModel.class));
        }

        @Test
        @DisplayName("Should pass validation when creating owner with age >= 18")
        void shouldPassValidationWhenCreatingAdultOwner() {
            UserModel inputUser = createUser(
                    "Adult", "Owner", "12345678", "+573001234567",
                    "adult@example.com", "password123", null, LocalDate.now().minusYears(20)
            );

            when(userPersistencePort.existsByEmail(inputUser.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(inputUser.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(inputUser.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(inputUser);

            UserModel result = userUseCase.createOwner(inputUser);

            assertNotNull(result);
            assertEquals(Role.OWNER, result.getRole());
        }

        @Test
        @DisplayName("Should throw exception when creating underage owner")
        void shouldThrowWhenCreatingUnderageOwner() {
            UserModel underageUser = createUser(
                    "Young", "Owner", "99999999", "+573001111111",
                    "young@example.com", "password123", null, LocalDate.now().minusYears(17)
            );

            DomainException exception = assertThrows(DomainException.class,
                    () -> userUseCase.createOwner(underageUser));

            assertEquals(String.format(UserMessages.USER_NOT_ADULT, "Young", "Owner"),
                    exception.getMessage());
            verify(userPersistencePort, never()).saveUser(any());
        }

        @Test
        @DisplayName("Should create employee without birth date")
        void shouldCreateEmployeeWithoutBirthDate() {
            UserModel inputUser = createUser(
                    "New", "Employee", "87654321", "+573008765432",
                    "employee@example.com", "password456", null, null
            );

            when(userPersistencePort.existsByEmail(inputUser.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(inputUser.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(inputUser.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(inputUser);

            UserModel result = userUseCase.createEmployee(inputUser);

            assertNotNull(result);
            assertEquals(Role.EMPLOYEE, result.getRole());
            assertNull(result.getBirthDate());
        }

        @Test
        @DisplayName("Should create customer without birth date")
        void shouldCreateCustomerWithoutBirthDate() {
            UserModel inputUser = createUser(
                    "New", "Customer", "11223344", "+573009876543",
                    "customer@example.com", "password789", null, null
            );

            when(userPersistencePort.existsByEmail(inputUser.getEmail())).thenReturn(false);
            when(userPersistencePort.existsByDocumentId(inputUser.getDocumentId())).thenReturn(false);
            when(passwordEncoderPort.encode(inputUser.getPassword())).thenReturn("encryptedPassword");
            when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(inputUser);

            UserModel result = userUseCase.createCustomer(inputUser);

            assertNotNull(result);
            assertEquals(Role.CUSTOMER, result.getRole());
            assertNull(result.getBirthDate());
        }
    }
}