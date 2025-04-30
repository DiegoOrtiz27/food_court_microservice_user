package com.foodquart.microserviceuser.application.mapper;

import com.foodquart.microserviceuser.application.dto.request.OwnerRequestDto;
import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOwnerRequestMapper {

    UserModel toUser(OwnerRequestDto ownerRequestDto);

    OwnerModel toOwner(OwnerRequestDto ownerRequestDto, UserModel userModel);
}