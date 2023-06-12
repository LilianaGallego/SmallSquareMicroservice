package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private IOrderServicePort orderUseCase;
    @BeforeEach
    void setUp() {
        orderUseCase = new OrderUseCase(orderPersistencePort, restaurantPersistencePort, restaurantEmployeePersistencePort);
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
        order.setStateEnum(StateEnum.EARNING);
        order.setRestaurant(restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StateEnum.CANCELLED.getName(),10L,restaurantEntity);

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
        assertEquals(StateEnum.EARNING, order.getStateEnum());
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
        order.setStateEnum(StateEnum.EARNING);
        order.setRestaurant(restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StateEnum.DELIVERED.getName(),10L,restaurantEntity);

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
        assertEquals(StateEnum.EARNING, order.getStateEnum());
        assertEquals(restaurant, order.getRestaurant());
        verify(orderPersistencePort, times(2)).saveOrder(order);
    }

    @Test
    void testValidateStatePreparation() {
        // Arrange
        OrderEntity orderBD = new OrderEntity();
        orderBD.setStateEnum(StateEnum.PREPARATION.getName());
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

    @Test
    void testGetAllOrdersByStateEnum() {
        // Arrange
        StateEnum stateEnum = StateEnum.CANCELLED;
        int page = 0;
        int size = 10;
        Long idEmployee = 123L;
        Long idRestaurant = 456L;
        List<OrderResponseDto> expectedOrders = new ArrayList<>();
        expectedOrders.add(new OrderResponseDto());
        expectedOrders.add(new OrderResponseDto());
        RestaurantEmployeeEntity restaurantEmployeeEntity = new RestaurantEmployeeEntity(10L,idEmployee,idRestaurant);

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee,idRestaurant);

        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(null)).thenReturn(restaurantEmployee);
        when(orderPersistencePort.getAllOrdersByStateEnum(stateEnum, idRestaurant, page, size)).thenReturn(expectedOrders);

        // Act
        List<OrderResponseDto> actualOrders = orderUseCase.getAllOrdersByStateEnum(stateEnum, page, size);

        // Assert
        assertEquals(expectedOrders, actualOrders);
        verify(restaurantEmployeePersistencePort, times(1)).getRestaurantEmployeeByIdEmployee(null);
        verify(orderPersistencePort, times(1)).getAllOrdersByStateEnum(stateEnum, idRestaurant, page, size);
    }
}