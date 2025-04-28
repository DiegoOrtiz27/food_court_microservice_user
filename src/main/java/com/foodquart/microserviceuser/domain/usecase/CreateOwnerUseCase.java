package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.api.IOwnerServicePort;
import com.foodquart.microserviceuser.domain.exception.*;
import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.domain.model.RoleModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IOwnerPersistencePort;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.util.ValidationUtil;

import java.time.LocalDate;
import java.time.Period;

public class CreateOwnerUseCase implements IOwnerServicePort {

    private final IOwnerPersistencePort ownerPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private static final int MAX_PHONE_LENGTH = 13;
    private static final long OWNER_ROLE_ID = 2L;


    public CreateOwnerUseCase(IOwnerPersistencePort ownerPersistencePort, IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.ownerPersistencePort = ownerPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void saveOwner(OwnerModel ownerModel, UserModel userModel) {

        validateRequiredFields(userModel);

        if (!ValidationUtil.isValidEmail(userModel.getEmail())) {
            throw new InvalidEmailFormatException(userModel.getEmail());
        }

        if (!ValidationUtil.isValidPhoneNumber(userModel.getPhone(), MAX_PHONE_LENGTH)) {
            throw new InvalidPhoneNumberException(userModel.getPhone());
        }

        if (!ValidationUtil.isNumericDocument(userModel.getDocumentId())) {
            throw new InvalidDocumentIdException(userModel.getDocumentId());
        }

        if (userPersistencePort.existsByEmail(userModel.getEmail())) {
            throw new EmailAlreadyExistsException(userModel.getEmail());
        }

        if (userPersistencePort.existsByDocumentId(userModel.getDocumentId())) {
            throw new DocumentIdAlreadyExistsException(userModel.getDocumentId());
        }

        if (!isAdult(userModel.getBirthDate())) {
            throw new UserNotAdultException(userModel.getFirstName(), userModel.getLastName());
        }
        RoleModel ownerRole = new RoleModel(OWNER_ROLE_ID, "OWNER");
        userModel.setRole(ownerRole);

        String encoded = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encoded);

        UserModel savedUser = userPersistencePort.saveUser(userModel);

        ownerModel.setUser(savedUser);

        ownerPersistencePort.saveOwner(ownerModel);
    }

    private boolean isAdult(LocalDate birthDate) {
        if (birthDate == null) return false;
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    private void validateRequiredFields(UserModel userModel) {
        if (userModel.getFirstName() == null || userModel.getFirstName().trim().isEmpty()) {
            throw new RequiredFieldException("Name");
        }
        if (userModel.getLastName() == null || userModel.getLastName().trim().isEmpty()) {
            throw new RequiredFieldException("Last name");
        }
        if (userModel.getDocumentId() == null || userModel.getDocumentId().trim().isEmpty()) {
            throw new RequiredFieldException("Identity Document");
        }
        if (userModel.getPhone() == null || userModel.getPhone().trim().isEmpty()) {
            throw new RequiredFieldException("Phone");
        }
        if (userModel.getBirthDate() == null) {
            throw new RequiredFieldException("Birthdate");
        }
        if (userModel.getEmail() == null || userModel.getEmail().trim().isEmpty()) {
            throw new RequiredFieldException("Email");
        }
        if (userModel.getPassword() == null || userModel.getPassword().trim().isEmpty()) {
            throw new RequiredFieldException("Password");
        }
    }
}
