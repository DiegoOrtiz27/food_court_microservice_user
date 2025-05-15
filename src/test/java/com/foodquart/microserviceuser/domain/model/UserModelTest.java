package com.foodquart.microserviceuser.domain.model;

import com.foodquart.microserviceuser.domain.util.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    @Test
    void shouldCreateUserModelWithAllArgsConstructor() {
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String documentId = "12345678";
        String phone = "+573001234567";
        String email = "john@example.com";
        String password = "password123";
        Role role = Role.OWNER;
        LocalDate birthDate = LocalDate.now().minusYears(25);

        UserModel user = new UserModel(id, firstName, lastName, documentId, phone, birthDate, email, password, role);

        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(documentId, user.getDocumentId());
        assertEquals(phone, user.getPhone());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(role, user.getRole());
    }

    @Test
    void shouldCreateUserModelWithNoArgsConstructorAndSetters() {
        Long id = 1L;
        String firstName = "Jane";
        String lastName = "Smith";
        String documentId = "87654321";
        String phone = "+573008765432";
        String email = "jane@example.com";
        String password = "password456";
        Role role = Role.EMPLOYEE;
        LocalDate birthDate = LocalDate.now().minusYears(30);

        UserModel user = new UserModel();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDocumentId(documentId);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthDate);
        user.setRole(role);

        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(documentId, user.getDocumentId());
        assertEquals(phone, user.getPhone());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(role, user.getRole());
    }

    @Test
    void shouldGetIdCorrectly() {
        UserModel user = new UserModel();
        Long id = 123L;
        user.setId(id);
        assertEquals(id, user.getId());
    }
}