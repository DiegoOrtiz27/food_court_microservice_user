package com.foodquart.microserviceuser.application.mapper;

import com.foodquart.microserviceuser.application.dto.request.LoginRequestDto;
import com.foodquart.microserviceuser.domain.model.AuthModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IAuthRequestMapper {

    AuthModel toAuth(LoginRequestDto loginRequestDto);
}
