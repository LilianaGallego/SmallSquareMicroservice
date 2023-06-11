package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeEntityMapper {
    RestaurantEmployeeEntity toEntity(RestaurantEmployee restaurantEmployee);

    RestaurantEmployee toRestaurantEmployee(RestaurantEmployeeEntity restaurantEmployeeEntity);
}
