package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testSaveOrderStateCancelled() {
        // Arrange
        Long idRestaurant = 1L;
        Long idClient = 2L;
        Order order = new Order();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        order.setIdClient(idClient);
        order.setDate(LocalDate.now());
        order.setState(StateEnum.EARNING);
        order.setRestaurant(restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StateEnum.CANCELLED,10L,restaurantEntity);

        when(restaurantPersistencePort.findById(idRestaurant)).thenReturn(restaurant);
        when(orderPersistencePort.existsByIdClient(idClient)).thenReturn(true);
        when(orderPersistencePort.findByIdClient(idClient)).thenReturn(orderEntity);
        doNothing().when(orderPersistencePort).saveOrder(order);
        TokenInterceptor.setIdUser(2L);
        // Act
        orderUseCase.saveOrder(idRestaurant, order);


        // Assert
        verify(restaurantPersistencePort, times(1)).findById(idRestaurant);
        assertEquals(idClient, order.getIdClient());
        assertEquals(LocalDate.now(), order.getDate());
        assertEquals(StateEnum.EARNING, order.getState());
        assertEquals(restaurant, order.getRestaurant());
        verify(orderPersistencePort, times(2)).saveOrder(order);
    }

    @Test
    void testSaveOrderStateDelivered() {
        // Arrange
        Long idRestaurant = 1L;
        Long idClient = 2L;
        Order order = new Order();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        order.setIdClient(idClient);
        order.setDate(LocalDate.now());
        order.setState(StateEnum.EARNING);
        order.setRestaurant(restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StateEnum.DELIVERED,10L,restaurantEntity);

        when(restaurantPersistencePort.findById(idRestaurant)).thenReturn(restaurant);
        when(orderPersistencePort.existsByIdClient(idClient)).thenReturn(true);
        when(orderPersistencePort.findByIdClient(idClient)).thenReturn(orderEntity);
        doNothing().when(orderPersistencePort).saveOrder(order);
        TokenInterceptor.setIdUser(2L);
        // Act
        orderUseCase.saveOrder(idRestaurant, order);


        // Assert
        verify(restaurantPersistencePort, times(1)).findById(idRestaurant);
        assertEquals(idClient, order.getIdClient());
        assertEquals(LocalDate.now(), order.getDate());
        assertEquals(StateEnum.EARNING, order.getState());
        assertEquals(restaurant, order.getRestaurant());
        verify(orderPersistencePort, times(2)).saveOrder(order);
    }

    @Test
    void testValidateStatePreparation() {
        // Arrange
        OrderEntity orderBD = new OrderEntity();
        orderBD.setState(StateEnum.PREPARATION);
        Order order = new Order();
        Restaurant restaurant = new Restaurant();

        // Act and Assert
        assertThrows(OrderInProcessesException.class, () -> orderUseCase.validateState(orderBD, order, restaurant));
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