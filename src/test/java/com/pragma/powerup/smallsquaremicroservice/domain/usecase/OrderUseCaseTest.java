package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    private IOrderServicePort orderUseCase;
    @BeforeEach
    void setUp() {
        orderUseCase = new OrderUseCase(orderPersistencePort, restaurantPersistencePort);
    }

    @Test
    void testSaveOrder() {
        // Arrange
        Long idRestaurant = 1L;
        Long idClient = 2L;
        Order order = new Order();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);

        when(restaurantPersistencePort.findById(idRestaurant)).thenReturn(restaurant);
        doNothing().when(orderPersistencePort).saveOrder(order);
        TokenInterceptor.setIdUser(2L);
        // Act
        orderUseCase.saveOrder(idRestaurant, order);

        // Assert
        verify(restaurantPersistencePort, times(1)).findById(idRestaurant);
        assertEquals(idClient, order.getIdClient());
        assertEquals(LocalDate.now(), order.getDate());
        assertEquals("Pendiente", order.getState());
        assertEquals(restaurant, order.getRestaurant());
        verify(orderPersistencePort, times(1)).saveOrder(order);
    }

    @Test
    void testSaveOrderPlate() {
        // Arrange
        OrderPlate orderPlate = new OrderPlate();
        doNothing().when(orderPersistencePort).saveOrderPlate(orderPlate);

        // Act
        orderUseCase.saveOrderPlate(orderPlate);

        // Assert
        verify(orderPersistencePort, times(1)).saveOrderPlate(orderPlate);
    }

}