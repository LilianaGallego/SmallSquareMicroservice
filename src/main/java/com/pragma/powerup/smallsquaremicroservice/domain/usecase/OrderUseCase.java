package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.exceptions.PlateNoFromRestautantException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.exception.UserNotRoleAuthorized;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.NotStatusInProcess;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IMessangerServicePersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort  restaurantPersistencePort;
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IMessangerServicePersistencePort messengerServicePersistencePort;
    @Setter
    private static int code;
    public static int getCode() {
        return code;
    }



    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IMessangerServicePersistencePort messengerServicePersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.messengerServicePersistencePort = messengerServicePersistencePort;
    }



    @Override
    public void saveOrder(Long idRestaurant, Order order) {
        Restaurant restaurant = restaurantPersistencePort.findById(idRestaurant);
        order.setIdClient(TokenInterceptor.getIdUser());
        if (orderPersistencePort.existsByIdClient(order.getIdClient())) {
            OrderEntity orderBD = orderPersistencePort.findByIdClient(order.getIdClient());
            validateState(orderBD,order,restaurant);
        }
        order.setDate(LocalDate.now());
        order.setRestaurant(restaurant);
        orderPersistencePort.saveOrder(order);

    }

    @Override
    public void saveOrderPlate(OrderPlate orderPlate) {

        orderPersistencePort.saveOrderPlate(orderPlate);
    }

    @Override
    public void validateState(OrderEntity orderBD, Order order, Restaurant restaurant) {
        switch (orderBD.getStateEnum().toString()){
            case "READY","EARNING","PREPARATION" -> throw new OrderInProcessesException();
            case "CANCELLED","DELIVERED" -> {
                order.setStateEnum(StateEnum.EARNING);
                order.setIdClient(TokenInterceptor.getIdUser());
                order.setDate(LocalDate.now());
                order.setRestaurant(restaurant);
                orderPersistencePort.saveOrder(order);
            }
        }

    }

    @Override
    public List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size) {

        if ( restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(TokenInterceptor.getIdUser())==null){
            throw new UserNotRoleAuthorized();
        }
        RestaurantEmployee restaurantEmployee = restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(TokenInterceptor.getIdUser());
        Long idRestaurant = restaurantEmployee.getIdRestaurant();
        return orderPersistencePort.getAllOrdersByStateEnum(stateEnum, idRestaurant,page, size);
    }

    @Override
    public List<OrderPlateResponseDto> getAllOrdersByOrder(OrderResponseDto order) {

        return orderPersistencePort.getAllOrdersByOrder(order);
    }

    @Override
    public List<OrderResponseDto> updateStatusOrder(Long idOrder, StateEnum stateEnum, int page, int size) {
        if(orderPersistencePort.existsById(idOrder)){
            Optional<OrderEntity> order = orderPersistencePort.findById(idOrder);
            Long idEmployee = TokenInterceptor.getIdUser();
            order.get().setStateEnum(stateEnum.name());
            validateRestaurant(order.get(),idEmployee);

            return orderPersistencePort.updateStatusOrder(order.get(), stateEnum, order.get().getRestaurantEntity().getId(), page, size);
        }
        throw new NoDataFoundException();

    }

    @Override
    public void validateRestaurant(OrderEntity orderBD,Long idEmployee) {
        Long idRestauratOrder = orderBD.getRestaurantEntity().getId();
        Long idRestauntEmployee = restaurantEmployeePersistencePort.getRestaurantEmployeeByIdEmployee(idEmployee).getIdRestaurant();
        if (!idRestauntEmployee.equals(idRestauratOrder)){
            throw new PlateNoFromRestautantException();
        }
        orderBD.setIdChef(idEmployee);

    }

    @Override
    public void updateOrderReady(Long idOrder,StateEnum stateEnum) {
        if(!orderPersistencePort.existsById(idOrder)){
            throw new NoDataFoundException();
        }
        Optional<OrderEntity> order = orderPersistencePort.findById(idOrder);
        Long idEmployee = TokenInterceptor.getIdUser();
        validateRestaurant(order.get(),idEmployee);
        validateStateOrder(order.get(),stateEnum);


    }
    @Override
    public void validateStateOrder(OrderEntity order, StateEnum stateEnum){

        if(!order.getStateEnum().equals(StateEnum.PREPARATION.toString())){
            throw new NotStatusInProcess();
        }
        order.setStateEnum(stateEnum.name());
        orderPersistencePort.updateOrderReady(order);
        sendMessageOrderReady(order);
    }

    @Override
    public void sendMessageOrderReady(OrderEntity order) {
        code = generateCode();
        String message= "Estimado cliente su pedido con id: " + order.getId()+ ".\nYa esta listo para reclamar con el siguiente codigo: " + code;
        messengerServicePersistencePort.sendMessageOrderReady(message);

    }
    @Override
    public int generateCode() {
        return (int) (10000 + Math.random() * 90000);
    }



}
