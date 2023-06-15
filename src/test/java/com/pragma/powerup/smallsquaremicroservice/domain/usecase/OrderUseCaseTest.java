package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.exceptions.PlateNoFromRestautantException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IMessangerServicePersistencePort;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    @Mock
    private IMessangerServicePersistencePort messengerServicePersistencePort;
    private IOrderServicePort orderUseCase;
    @BeforeEach
    void setUp() {
        orderUseCase = new OrderUseCase(orderPersistencePort, restaurantPersistencePort, restaurantEmployeePersistencePort, messengerServicePersistencePort);
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
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(10L);
        List<OrderPlateResponseDto> orderPlates = new ArrayList<>();
        OrderPlateResponseDto orderPlate = new OrderPlateResponseDto(10L,10);
        orderPlates.add(orderPlate);
        orderResponseDto.setOrderPlates(orderPlates);
        List<OrderResponseDto> expectedOrders = new ArrayList<>();
        expectedOrders.add(orderResponseDto);
        expectedOrders.add(new OrderResponseDto());
        TokenInterceptor.setIdUser(idEmployee);

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee,idRestaurant);

        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);
        when(orderPersistencePort.getAllOrdersByStateEnum(stateEnum, idRestaurant, page, size)).thenReturn(expectedOrders);

        // Act
        List<OrderResponseDto> actualOrders = orderUseCase.getAllOrdersByStateEnum(stateEnum, page, size);

        // Assert
        assertEquals(expectedOrders, actualOrders);
        verify(restaurantEmployeePersistencePort, times(1)).getRestaurantEmployeeByIdEmployee(idEmployee);
        verify(orderPersistencePort, times(1)).getAllOrdersByStateEnum(stateEnum, idRestaurant, page, size);
    }

    @Test
    void testUpdateStatusOrder_ExistingOrder_Success() {
        // Arrange
        Long idOrder = 1L;
        StateEnum stateEnum = StateEnum.EARNING;
        int page = 0;
        int size = 10;
        Long idEmployee = 2L;
        Order order = new Order();
        order.setId(idOrder);
        order.setStateEnum(stateEnum);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(3L);
        orderEntity.setStateEnum(StateEnum.PREPARATION.getName());
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        TokenInterceptor.setIdUser(idEmployee);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, 3L);

        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));
        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);
        when(orderPersistencePort.updateStatusOrder(orderEntity, stateEnum, 3L, page, size))
                .thenReturn(Collections.singletonList(new OrderResponseDto()));

        // Act
        List<OrderResponseDto> result = orderUseCase.updateStatusOrder(idOrder, stateEnum, page, size);
        orderUseCase.validateRestaurant(orderEntity, idEmployee);
        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(orderEntity.getStateEnum(), stateEnum.name());
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(restaurantEmployeePersistencePort, times(2)).getRestaurantEmployeeByIdEmployee(2L);
        verify(orderPersistencePort, times(1)).updateStatusOrder(orderEntity, stateEnum, orderEntity.getRestaurantEntity().getId(), page, size);
    }

    @Test
    void testUpdateStatusOrder_NonExistingOrder_ExceptionThrown() {
        // Arrange
        Long idOrder = 1L;
        StateEnum stateEnum = StateEnum.READY;
        int page = 0;
        int size = 10;

        when(orderPersistencePort.existsById(idOrder)).thenReturn(false);

        // Act and Assert
        assertThrows(NoDataFoundException.class, () -> orderUseCase.updateStatusOrder(idOrder, stateEnum, page, size));
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, never()).findById(anyLong());
        verify(restaurantEmployeePersistencePort, never()).getRestaurantEmployeeByIdEmployee(anyLong());
        verify(orderPersistencePort, never()).updateStatusOrder(any(), any(), anyLong(), anyInt(), anyInt());
    }

    @Test
    void testValidateRestaurant_ValidRestaurant_Success() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        orderEntity.setRestaurantEntity(restaurantEntity);
        Long idEmployee = 2L;
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee,3L);


        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee))
                .thenReturn(restaurantEmployee);

        // Act and Assert
        assertDoesNotThrow(() -> orderUseCase.validateRestaurant(orderEntity, idEmployee));
        assertEquals(idEmployee, orderEntity.getIdChef());
        verify(restaurantEmployeePersistencePort, times(1)).getRestaurantEmployeeByIdEmployee(idEmployee);
    }

    @Test
    void testValidateRestaurant_InvalidRestaurant_ExceptionThrown() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(2L);

        orderEntity.setRestaurantEntity(restaurantEntity);
        Long idEmployee = 2L;
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee,3L);


        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee))
                .thenReturn(restaurantEmployee);

        // Act and Assert
        assertThrows(PlateNoFromRestautantException.class, () -> orderUseCase.validateRestaurant(orderEntity, idEmployee));
        assertNull(orderEntity.getIdChef());
        verify(restaurantEmployeePersistencePort, times(1)).getRestaurantEmployeeByIdEmployee(idEmployee);
    }

    @Test
    void testUpdateOrderReady_ExistingOrder_Success() {
        // Arrange
        Long idOrder = 1L;
        StateEnum stateEnum = StateEnum.PREPARATION;
        Long idEmployee = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(stateEnum.toString());
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee,3L);
        TokenInterceptor.setIdUser(idEmployee);

        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));
        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);

        // Act
        orderUseCase.validateStateOrder(orderEntity,stateEnum);
        assertDoesNotThrow(() -> orderUseCase.updateOrderReady(idOrder, stateEnum));


        // Assert
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(messengerServicePersistencePort, times(2)).sendMessageOrderReady(anyString());
    }

    @Test
    void testUpdateOrderReady_NonExistingOrder_ExceptionThrown() {
        // Arrange
        Long idOrder = 1L;
        StateEnum stateEnum = StateEnum.READY;;

        when(orderPersistencePort.existsById(idOrder)).thenReturn(false);

        // Act and Assert
        assertThrows(NoDataFoundException.class, () -> orderUseCase.updateOrderReady(idOrder, stateEnum));
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, never()).findById(anyLong());
        verify(restaurantEmployeePersistencePort, never()).getRestaurantEmployeeByIdEmployee(anyLong());
        verify(orderPersistencePort, never()).updateStatusOrder(any(), any(), anyLong(), anyInt(), anyInt());
    }
}