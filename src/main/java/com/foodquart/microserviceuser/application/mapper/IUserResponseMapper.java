package com.foodquart.microserviceuser.application.mapper;

import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;
import com.foodquart.microserviceuser.application.dto.response.GetUserInfoResponseDto;
import com.foodquart.microserviceuser.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
    CreateUserResponseDto toResponse(String response, Long userId);

    @Mapping(target = "userId", source = "id")
    GetUserInfoResponseDto toResponse(UserModel userModel);
}
