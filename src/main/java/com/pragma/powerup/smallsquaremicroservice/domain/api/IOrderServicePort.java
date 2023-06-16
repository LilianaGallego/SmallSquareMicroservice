package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant, Order order);
    void saveOrderPlate(OrderPlate orderPlate);
    void validateState(OrderEntity orderBD, Order order, Restaurant restaurant);
    void validateRestaurant(OrderEntity orderBD, Long idEmployee);
    void validatePhoneClient(OrderEntity order, StateEnum stateEnum);
    List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size);

    List<OrderPlateResponseDto> getAllOrdersByOrder(OrderResponseDto order);
    List<OrderResponseDto> updateStatusOrder(Long idOrder, StateEnum stateEnum, int page, int size);

    void updateOrderReady(Long idOrder, StateEnum stateEnum);
    void sendMessageOrderReady(OrderEntity order, int code);
    int generateCode();

    void validateStateOrder(OrderEntity order, StateEnum stateEnum);

}
