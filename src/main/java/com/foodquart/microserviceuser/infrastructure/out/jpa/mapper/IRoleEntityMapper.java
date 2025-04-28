package com.foodquart.microserviceuser.infrastructure.out.jpa.mapper;

import com.foodquart.microserviceuser.domain.model.RoleModel;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleEntityMapper {
    RoleModel toRoleModel(RoleEntity roleEntity);
    RoleEntity toRoleEntity(RoleModel roleModel);
}
