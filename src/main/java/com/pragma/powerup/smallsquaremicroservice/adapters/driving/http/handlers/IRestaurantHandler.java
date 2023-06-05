package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.RestaurantPageableResponseDto;

import java.util.List;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
    void addEmployee(Long idRestaurant, EmployeeRequestDto user);
    List<RestaurantPageableResponseDto> getAllRestaurants(int page, int pageSize);
}
