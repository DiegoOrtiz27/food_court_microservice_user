package com.foodquart.microserviceuser.application.mapper;

import com.foodquart.microserviceuser.application.dto.response.GetUserByEmailResponseDto;
import com.foodquart.microserviceuser.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {

    GetUserByEmailResponseDto toResponse(UserModel user);
}
