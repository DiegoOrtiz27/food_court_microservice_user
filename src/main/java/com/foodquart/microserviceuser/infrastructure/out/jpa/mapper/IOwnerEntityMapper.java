package com.foodquart.microserviceuser.infrastructure.out.jpa.mapper;


import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.OwnerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOwnerEntityMapper {

    OwnerEntity toEntity(OwnerModel owner);

    OwnerModel toOwnerModel(OwnerEntity ownerEntity);
}