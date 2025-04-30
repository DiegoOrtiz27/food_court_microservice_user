package com.foodquart.microserviceuser.infrastructure.out.jpa.mapper;

import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {

    UserEntity toEntity(UserModel model);

    UserModel toUserModel(UserEntity entity);
}