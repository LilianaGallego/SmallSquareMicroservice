package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.PlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlateResponseMapper {

    @Mapping(target = "idRestaurant", source = "restaurant.id")
    @Mapping(target = "idCategory", source = "category.id")
    PlateResponseDto toResponse(Plate plate);

    List<PlateResponseDto> toResponseList(List<Plate> userList);
}
