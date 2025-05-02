package com.foodquart.microserviceuser.application.mapper;

import com.foodquart.microserviceuser.application.dto.response.LoginResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IAuthResponseMapper {

    LoginResponseDto toResponse(String token);
}
