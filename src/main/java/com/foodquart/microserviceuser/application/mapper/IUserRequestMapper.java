package com.foodquart.microserviceuser.application.mapper;

import com.foodquart.microserviceuser.application.dto.request.CreateEmployeeRequestDto;
import com.foodquart.microserviceuser.application.dto.request.CreateOwnerRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;
import com.foodquart.microserviceuser.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {

    UserModel toUser(CreateOwnerRequestDto createOwnerRequestDto);

    UserModel toUser(CreateEmployeeRequestDto createEmployeeRequestDto);

    CreateUserResponseDto toResponse(String response, Long userId);
}