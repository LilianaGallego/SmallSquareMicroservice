package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderPlateResponseMapper {

    @Mapping(target = "idPlate", source = "plate.id")
    OrderPlateResponseDto toResponse(OrderPlate orderPlate);


    @Mapping(target = "plate.id", source = "idPlate")
    OrderPlate toOrderPlate(OrderPlateResponseDto orderPlateResponseDto);

    List<OrderPlateResponseDto> toResponseList(List<OrderPlate> orderList);
    List<OrderPlate> toOrderPlateList(List<OrderPlateResponseDto> orderList);

}
