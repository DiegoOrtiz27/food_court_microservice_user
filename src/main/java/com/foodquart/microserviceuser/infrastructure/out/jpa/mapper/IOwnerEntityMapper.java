package com.foodquart.microserviceuser.infrastructure.out.jpa.mapper;


import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.OwnerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOwnerEntityMapper {

    OwnerEntity toOwnerEntity(OwnerModel owner);

    OwnerModel toOwnerModel(OwnerEntity ownerEntity);
}