package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.OrderPlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IOrderHandler;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Mock
    private IOrderHandler orderHandler;

    @InjectMocks
    private OrderRestController restController;

    private OrderRequestDto orderRequestDto;

    @BeforeEach
    void setUp() {
        List<OrderPlateRequestDto> orderPlates = new ArrayList<>();
        OrderPlateRequestDto orderPlateRequestDto = new OrderPlateRequestDto(1L,10);
        orderPlates.add(orderPlateRequestDto);

        OrderRequestDto dto = new OrderRequestDto(1L, orderPlates);
    }

    @Test
    @DisplayName("Given a valid order, when savePlate is called, then a CREATED response is returned")
    void testSaveOrder() {
        // Arrange
        Mockito.doNothing().when(orderHandler).saveOrder(orderRequestDto);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = restController.saveOrder(orderRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Constants.ORDER_CREATED_MESSAGE, responseEntity.getBody().get(Constants.RESPONSE_MESSAGE_KEY));
    }


}