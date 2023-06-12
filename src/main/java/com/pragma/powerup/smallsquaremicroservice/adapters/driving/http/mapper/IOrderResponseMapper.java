package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    @Mapping(target = "idRestaurant", source = "restaurant.id")
    @Mapping(target = "orderPlates", source = "orderPlates")
    OrderResponseDto toResponse(Order order);

    @Mapping(target = "restaurant.id", source = "idRestaurant")
    @Mapping(target = "orderPlates", source = "orderPlates")
    Order toOrder(OrderResponseDto orderResponseDto);
    List<OrderResponseDto> toResponseList(List<Order> orderList);
}
