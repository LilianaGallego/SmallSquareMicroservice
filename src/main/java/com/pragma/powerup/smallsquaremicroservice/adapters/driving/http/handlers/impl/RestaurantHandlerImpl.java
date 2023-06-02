package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.RestaurantPageableResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IRestaurantPageableResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantPageableResponseMapper restaurantPageableResponseMapper;


    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequestDto));
    }

    @Override
    public List<RestaurantPageableResponseDto> getAllRestaurants(int page, int pageSize) {
        return restaurantPageableResponseMapper.toResponseList(restaurantServicePort.getAllRestaurants(page, pageSize));
    }

}
