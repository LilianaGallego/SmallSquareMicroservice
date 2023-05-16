package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlateRequestMapper {
    @Mapping(target = "restaurant.id", source = "idRestaurant")
    @Mapping(target = "category.id", source = "idCategory")
    Plate toPlate(PlateRequestDto plateRequestDto);
}
