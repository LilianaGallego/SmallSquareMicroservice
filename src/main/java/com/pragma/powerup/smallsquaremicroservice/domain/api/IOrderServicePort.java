package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant, Order order);
    void saveOrderPlate(OrderPlate orderPlate);
    void validateState(OrderEntity orderBD, Order order, Restaurant restaurant);
    List<Order> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size);

}
