package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RestaurantHandlerImplTest {
    private RestaurantHandlerImpl restaurantHandler;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantHandler = new RestaurantHandlerImpl(restaurantServicePort, restaurantRequestMapper);
    }

    @Test
    public void saveUser_ShouldCallSaveUserInServicePort() {
        // Arrange
        RestaurantRequestDto dto = new RestaurantRequestDto("Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");
        Restaurant restaurant = new Restaurant();
        Mockito.when(restaurantRequestMapper.toRestaurant(dto)).thenReturn(restaurant);

        // Act
        restaurantHandler.saveRestaurant(dto);

        // Assert
        verify(restaurantServicePort, times(1)).saveRestaurant(restaurant);
    }
}