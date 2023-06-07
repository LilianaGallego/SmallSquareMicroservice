package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.PlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.RestaurantPageableResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IPlateHandler;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlateRestControllerTest {

    @Mock
    private IPlateHandler plateHandler;

    @InjectMocks
    private PlateRestController restController;

    private PlateRequestDto plateRequestDto;
    private UpdatePlateRequestDto updatePlateRequestDto;


    @BeforeEach
    void setUp() {
        PlateRequestDto dto = new PlateRequestDto(
                "Frijolada",15000,"Bandeja con frijol,arroz, maduro, chicharron","url",1L,1L);
    }

    @Test
    @DisplayName("Given a valid plate, when savePlate is called, then a CREATED response is returned")
    void testSavePlate() {
        // Arrange
        Mockito.doNothing().when(plateHandler).savePlate(plateRequestDto);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = restController.savePlate(plateRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Constants.PLATE_CREATED_MESSAGE, responseEntity.getBody().get(Constants.RESPONSE_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Given a valid plate, when updatePlate is called, then a UPDATED response is returned")
    void testUpdatePlate() {
        // Arrange
        Mockito.doNothing().when(plateHandler).updatePlate(1L,updatePlateRequestDto);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = restController.updatePlate(1L,updatePlateRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Constants.PLATE_UPDATED_MESSAGE, responseEntity.getBody().get(Constants.RESPONSE_MESSAGE_KEY));
    }

    @Test
    void testGetAllPlates() {
        // Arrange
        int page = 1;
        int size = 10;
        Long idRestaurqant =1L;
        Long idCategory=1L;
        List<PlateResponseDto> plateResponseDtos = Arrays.asList(
                new PlateResponseDto("Las Delicias", 10000,"Bandeja con frijol,arroz, maduro, chicharron","url",1L,1L),
                new PlateResponseDto("Las 3 B", 7000,"Bandeja con frijol,arroz, maduro, chicharron","url",1L,1L));


        Mockito.when(plateHandler.getAllPlatesByRestaurant(idRestaurqant,idCategory,page, size)).thenReturn(plateResponseDtos);

        // Act
        ResponseEntity<List<PlateResponseDto>> response = restController.getAllPlatesByRestaurant(idRestaurqant,idCategory,page, size);

        // Assert
        verify(plateHandler, times(1)).getAllPlatesByRestaurant(idRestaurqant,idCategory,page, size);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(plateResponseDtos, response.getBody());
    }

}