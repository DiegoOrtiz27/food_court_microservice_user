package com.foodquart.microserviceuser.application.mapper;

import com.foodquart.microserviceuser.application.dto.request.OwnerRequestDto;
import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.domain.model.RoleModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = { RoleModel.class })
public interface IOwnerRequestMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "documentId", source = "documentId")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    UserModel toUserModel(OwnerRequestDto ownerRequestDto);

    @Mapping(target = "user", source = "userModel")
    OwnerModel toOwnerModel(OwnerRequestDto ownerRequestDto, UserModel userModel);
}