package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.api.IUserServicePort;
import com.foodquart.microserviceuser.domain.exception.*;
import com.foodquart.microserviceuser.domain.util.Role;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private static final int MAX_PHONE_LENGTH = 13;

    @Override
    public void saveUser(UserModel userModel) {
        validateRequiredFields(userModel);
        validateFormat(userModel);
        validateUniqueness(userModel);
        validateByRole(userModel);

        String encryptedPassword = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encryptedPassword);

        userPersistencePort.saveUser(userModel);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userPersistencePort.findByEmail(email);
    }

    private void validateRequiredFields(UserModel user) {
        if (isBlank(user.getFirstName())) {
            throw new RequiredFieldException("Name");
        }
        if (isBlank(user.getLastName())) {
            throw new RequiredFieldException("Last name");
        }
        if (isBlank(user.getDocumentId())) {
            throw new RequiredFieldException("Document ID");
        }
        if (isBlank(user.getPhone())) {
            throw new RequiredFieldException("Phone");
        }
        if (user.getBirthDate() == null) {
            throw new RequiredFieldException("Birthdate");
        }
        if (isBlank(user.getEmail())) {
            throw new RequiredFieldException("Email");
        }
        if (isBlank(user.getPassword())) {
            throw new RequiredFieldException("Password");
        }
        if (user.getRole() == null) {
            throw new RequiredFieldException("Role");
        }
    }

    private void validateFormat(UserModel user) {
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new InvalidEmailFormatException(user.getEmail());
        }
        if (!ValidationUtil.isValidPhoneNumber(user.getPhone(), MAX_PHONE_LENGTH)) {
            throw new InvalidPhoneNumberException(user.getPhone());
        }
        if (!ValidationUtil.isNumericDocument(user.getDocumentId())) {
            throw new InvalidDocumentIdException(user.getDocumentId());
        }
    }

    private void validateUniqueness(UserModel user) {
        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        if (userPersistencePort.existsByDocumentId(user.getDocumentId())) {
            throw new DocumentIdAlreadyExistsException(user.getDocumentId());
        }
    }

    private void validateByRole(UserModel user) {
        if ((user.getRole() == Role.OWNER || user.getRole() == Role.ADMIN) && !isAdult(user.getBirthDate())) {
            throw new UserNotAdultException(user.getFirstName(), user.getLastName());
        }
    }

    private boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}