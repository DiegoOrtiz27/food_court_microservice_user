package com.foodquart.microserviceuser.infrastructure.out.jpa.mapper;

import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = { IRoleEntityMapper.class }
)
public interface IUserEntityMapper {

    @Mapping(source = "id",         target = "id")
    @Mapping(source = "firstName",  target = "firstName")
    @Mapping(source = "lastName",   target = "lastName")
    @Mapping(source = "documentId", target = "documentId")
    @Mapping(source = "phone",      target = "phone")
    @Mapping(source = "birthDate",  target = "birthDate")
    @Mapping(source = "email",      target = "email")
    @Mapping(source = "password",   target = "password")
    @Mapping(source = "role",       target = "role")
    UserEntity toUserEntity(UserModel model);

    UserModel toUserModel(UserEntity entity);
}