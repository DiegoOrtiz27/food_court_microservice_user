package com.foodquart.microserviceuser.infrastructure.out.jpa.mapper;

import com.foodquart.microserviceuser.domain.model.RoleModel;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    RoleModel toRoleModel(RoleEntity roleEntity);
    RoleEntity toEntity(RoleModel roleModel);
}
