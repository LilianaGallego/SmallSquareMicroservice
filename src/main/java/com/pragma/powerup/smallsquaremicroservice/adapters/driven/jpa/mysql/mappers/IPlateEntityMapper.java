package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.PlateEntity;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlateEntityMapper {
    @Mapping(target = "restaurantEntity.id", source = "restaurant.id")
    PlateEntity toEntity(Plate plate);

    @Mapping(target = "restaurant.id", source = "restaurantEntity.id")
   Plate toUser(PlateEntity plateEntity);
}
