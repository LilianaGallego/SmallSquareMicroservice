package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.RestaurantPageableResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @Mock
    private IRestaurantHandler restaurantHandler;

    @InjectMocks
    private RestaurantRestController restController;

    private RestaurantRequestDto restaurantRequestDto;

    @BeforeEach
    void setUp() {
        restaurantRequestDto = new RestaurantRequestDto("Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");
    }

    @Test
    @DisplayName("Given a valid user, when saveUser is called, then a CREATED response is returned")
    void testSaveUser() {
        // Arrange
        Mockito.doNothing().when(restaurantHandler).saveRestaurant(restaurantRequestDto);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = restController.saveRestaurant(restaurantRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Constants.RESTAURANT_CREATED_MESSAGE, responseEntity.getBody().get(Constants.RESPONSE_MESSAGE_KEY));
    }

    @Test
    void testAddEmployee(){
        Long idRestaurant = 1L;
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setDniNumber("12345678");

        RestaurantRestController restaurantController = new RestaurantRestController(restaurantHandler);

        // Act
        ResponseEntity<Map<String, String>> response = restaurantController.addEmployee(employeeRequestDto, idRestaurant);

        // Assert
        verify(restaurantHandler, times(1)).addEmployee(idRestaurant, employeeRequestDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey(Constants.RESPONSE_MESSAGE_KEY));
        assertEquals(Constants.USER_CREATED_MESSAGE, response.getBody().get(Constants.RESPONSE_MESSAGE_KEY));

    }

    @Test
    void testGetAllRestaurants() {
        // Arrange
        int page = 1;
        int size = 10;
        List<RestaurantPageableResponseDto> restaurants = Arrays.asList(
                new RestaurantPageableResponseDto("Las Delicias", "Restaurant 1"),
                new RestaurantPageableResponseDto("Las 3 B", "Restaurant 2")
        );


        Mockito.when(restaurantHandler.getAllRestaurants(page, size)).thenReturn(restaurants);

        // Act
        ResponseEntity<List<RestaurantPageableResponseDto>> response = restController.getAllRestaurants(page, size);

        // Assert
        verify(restaurantHandler, times(1)).getAllRestaurants(page, size);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(restaurants, response.getBody());
    }







}