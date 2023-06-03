package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IEmployeeRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IEmployeeRequestMapper employeeRequestMapper;

    private final IRestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequestDto));
    }

    @Override
    public void addEmployee(Long idRestaurant, EmployeeRequestDto employeeRequestDto) {
        restaurantServicePort.addEmployee(employeeRequestDto, idRestaurant);

    }



}
