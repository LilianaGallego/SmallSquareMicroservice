package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.exceptions.PlateNoFromRestautantException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.exception.UserNotRoleAuthorized;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.IncorrectCodeException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.NotStatusInProcess;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.OrderNotCancellException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PhoneClientInvalidException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.*;
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

    @Mock
    private IUserHttpPersistencePort userHttpPersistencePort;
    private IOrderServicePort orderUseCase;
    @BeforeEach
    void setUp() {
        orderUseCase = new OrderUseCase(orderPersistencePort, restaurantPersistencePort, restaurantEmployeePersistencePort, messengerServicePersistencePort, userHttpPersistencePort);
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
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StateEnum.CANCELLED.toString(),10L,restaurantEntity,10);

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
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StateEnum.DELIVERED.toString(),10L,restaurantEntity, 10);

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
        orderBD.setStateEnum(StateEnum.READY.toString());
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
    void testGetAllOrdersByStateEnum_RestaurantEmployeeExists_ReturnsOrders() {
        // Arrange
        StateEnum stateEnum = StateEnum.CANCELLED;
        int page = 0;
        int size = 10;
        Long idEmployee = 123L;
        Long idRestaurant = 456L;
        List<OrderResponseDto> expectedOrders = new ArrayList<>();
        expectedOrders.add(new OrderResponseDto());
        expectedOrders.add(new OrderResponseDto());
        TokenInterceptor.setIdUser(idEmployee);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee,idRestaurant);

        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);
        when(orderPersistencePort.getAllOrdersByStateEnum(stateEnum, idRestaurant, page, size)).thenReturn(expectedOrders);

        // Act
        List<OrderResponseDto> actualOrders = orderUseCase.getAllOrdersByStateEnum(stateEnum, page, size);

        // Assert
        assertEquals(expectedOrders, actualOrders);
        verify(restaurantEmployeePersistencePort, times(2)).getRestaurantEmployeeByIdEmployee(idEmployee);
        verify(orderPersistencePort, times(1)).getAllOrdersByStateEnum(stateEnum, idRestaurant, page, size);
    }

    @Test
    void testGetAllOrdersByStateEnum_RestaurantEmployeeDoesNotExist_ThrowsUserNotRoleAuthorized() {
        // Arrange
        StateEnum stateEnum = StateEnum.EARNING;
        int page = 0;
        int size = 10;
        Long idUser = 1L;
        TokenInterceptor.setIdUser(idUser);
        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idUser)).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotRoleAuthorized.class, () -> orderUseCase.getAllOrdersByStateEnum(stateEnum, page, size));

    }

    @Test
    void testGetAllOrdersByOrder_ReturnsOrders() {
        // Arrange
        Long idRestaurant = 1L;

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(1L);
        List<OrderPlateResponseDto> orderResponseDtos = new ArrayList<>();
        OrderPlateResponseDto order = new OrderPlateResponseDto(1L, 10);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        orderResponseDtos.add(order);
        when(orderPersistencePort.getAllOrdersByOrder(orderResponseDto)).thenReturn(orderResponseDtos);

        // Act
        List<OrderPlateResponseDto> result = orderUseCase.getAllOrdersByOrder(orderResponseDto);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(orderPersistencePort, times(1)).getAllOrdersByOrder(orderResponseDto);
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
    void testUpdateOrder_ExistingOrder_Preparation_Success() {
        // Arrange
        Long idOrder = 1L;
        int codeClient = 1234;
        Long idEmployee = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.PREPARATION.toString());
        orderEntity.setCode(codeClient);
        orderEntity.setIdClient(1L);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, 3L);
        TokenInterceptor.setIdUser(idEmployee);
        User user = new User();
        user.setId(1L);
        user.setPhone("+573118688145");
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));
        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);
        when(userHttpPersistencePort.getClient(orderEntity.getIdClient())).thenReturn(user);

        // Act
        assertDoesNotThrow(() -> orderUseCase.updateOrder(idOrder, codeClient));

        // Assert
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(restaurantEmployeePersistencePort, times(1)).getRestaurantEmployeeByIdEmployee(idEmployee);
        verify(userHttpPersistencePort, times(1)).getClient(user.getId());
        verify(orderPersistencePort, times(1)).updateOrder(orderEntity);
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());

        assertEquals(StateEnum.READY.toString(), orderEntity.getStateEnum());

    }

    @Test
    void testUpdateOrder_ExistingOrder_Ready_Success() {
        // Arrange
        Long idOrder = 1L;
        int codeClient = 1234;
        Long idEmployee = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.READY.toString());
        orderEntity.setCode(codeClient);
        orderEntity.setIdClient(1L);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, 3L);
        TokenInterceptor.setIdUser(idEmployee);
        User user = new User();
        user.setId(1L);
        user.setPhone("+573118688145");
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));
        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);
        when(userHttpPersistencePort.getClient(orderEntity.getIdClient())).thenReturn(user);

        // Act
        assertDoesNotThrow(() -> orderUseCase.updateOrder(idOrder, codeClient));

        // Assert
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(restaurantEmployeePersistencePort, times(1)).getRestaurantEmployeeByIdEmployee(idEmployee);
        verify(userHttpPersistencePort, times(1)).getClient(user.getId());
        verify(orderPersistencePort, times(1)).updateOrder(orderEntity);
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());
        assertEquals(StateEnum.DELIVERED.toString(), orderEntity.getStateEnum());

        verify(orderPersistencePort, times(1)).updateOrder(orderEntity);
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());
    }

    @Test
    void testUpdateOrderReady_NonExistingOrder_ExceptionThrown() {
        // Arrange
        Long idOrder = 1L;
        StateEnum stateEnum = StateEnum.READY;

        when(orderPersistencePort.existsById(idOrder)).thenReturn(false);

        // Act and Assert
        assertThrows(NoDataFoundException.class, () -> orderUseCase.updateOrder(idOrder, 10));
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, never()).findById(anyLong());
        verify(restaurantEmployeePersistencePort, never()).getRestaurantEmployeeByIdEmployee(anyLong());
        verify(orderPersistencePort, never()).updateStatusOrder(any(), any(), anyLong(), anyInt(), anyInt());
    }

    @Test
    void testUpdateOrder_ExistingOrder_NotInProcess_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        int codeClient = 1234;
        Long idEmployee = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.DELIVERED.toString());
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, 3L);
        TokenInterceptor.setIdUser(idEmployee);
        User user = new User();
        user.setId(1L);
        user.setPhone("+573118688145");
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));
        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);


        // Act & Assert
        assertThrows(NotStatusInProcess.class, () -> orderUseCase.updateOrder(idOrder, codeClient));
    }

    @Test
    void testUpdateOrder_ExistingOrder_InvalidPhone_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        int codeClient = 1234;
        String phone= "9292929292";
        Long idEmployee = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.PREPARATION.toString());
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, 3L);
        TokenInterceptor.setIdUser(idEmployee);
        User user = new User();
        user.setId(1L);
        orderEntity.setIdClient(user.getId());
        user.setPhone("3119879489");
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));
        when(restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee)).thenReturn(restaurantEmployee);
        when(userHttpPersistencePort.getClient(orderEntity.getIdClient())).thenReturn(user);


        // Act & Assert
        assertNotEquals(user.getPhone(),phone);
        assertThrows(PhoneClientInvalidException.class, () -> orderUseCase.updateOrder(idOrder, codeClient));
    }

    @Test
    void testUpdateOrder_ExistingOrder_IncorrectCode_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        int codeClient = 1234;
        Long idEmployee = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.READY.toString());
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(3L);
        orderEntity.setRestaurantEntity(restaurantEntity);
        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, 3L);
        TokenInterceptor.setIdUser(idEmployee);
        User user = new User();
        user.setId(1L);
        user.setPhone("+573118688145");
        orderEntity.setCode(codeClient + 1); // Set an incorrect code

        // Act & Assert
        assertNotEquals(orderEntity.getCode(),codeClient);
        assertThrows(IncorrectCodeException.class, () -> orderUseCase.validateCodeClient(orderEntity, codeClient));
    }

    @Test
    void testCancelOrder_ExistingOrder_EarningState_Success() {
        // Arrange
        Long idOrder = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.EARNING.toString());
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));

        // Act
        assertDoesNotThrow(() -> orderUseCase.cancelOrder(idOrder));
        assertDoesNotThrow(() -> messengerServicePersistencePort.sendMessageStateOrderUpdated("message"));


        // Assert
        verify(orderPersistencePort, times(1)).existsById(idOrder);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(orderPersistencePort, times(1)).updateOrder(orderEntity);
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());
    }

    @Test
    void testCancelOrder_ExistingOrder_PreparationState_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.PREPARATION.toString());
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        assertThrows(OrderNotCancellException.class, () -> orderUseCase.cancelOrder(idOrder));
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());
    }

    @Test
    void testCancelOrder_ExistingOrder_ReadyState_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.READY.toString());
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        assertThrows(OrderNotCancellException.class, () -> orderUseCase.cancelOrder(idOrder));
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());
    }

    @Test
    void testCancelOrder_ExistingOrder_DeliveredState_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.DELIVERED.toString());
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        assertThrows(NotStatusInProcess.class, () -> orderUseCase.cancelOrder(idOrder));
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());
    }

    @Test
    void testCancelOrder_ExistingOrder_CancelledState_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(idOrder);
        orderEntity.setStateEnum(StateEnum.CANCELLED.toString());
        when(orderPersistencePort.existsById(idOrder)).thenReturn(true);
        when(orderPersistencePort.findById(idOrder)).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        assertThrows(NotStatusInProcess.class, () -> orderUseCase.cancelOrder(idOrder));
        verify(messengerServicePersistencePort, times(1)).sendMessageStateOrderUpdated(anyString());
    }

    @Test
    void testCancelOrder_NonExistingOrder_ThrowsException() {
        // Arrange
        Long idOrder = 1L;
        when(orderPersistencePort.existsById(idOrder)).thenReturn(false);

        // Act & Assert
        assertThrows(NoDataFoundException.class, () -> orderUseCase.cancelOrder(idOrder));
    }
}