package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderPlateEntity;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderPlateEntityMapper {
    @Mapping(target = "orderEntity.id", source = "order.id")
    @Mapping(target = "plateEntity.id", source = "plate.id")
    OrderPlateEntity toEntity(OrderPlate orderPlate);

    @Mapping(target = "order.id", source = "orderEntity.id")
    @Mapping(target = "plate.id", source = "plateEntity.id")
    OrderPlate toOrderPlate(OrderPlateEntity orderPlate);
}
