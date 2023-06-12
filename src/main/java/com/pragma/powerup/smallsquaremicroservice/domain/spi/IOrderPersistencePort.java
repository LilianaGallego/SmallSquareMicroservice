package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.util.List;

public interface IOrderPersistencePort {

    void saveOrder(Order order);
    void saveOrderPlate(OrderPlate orderPlate);
    boolean existsByIdClient(Long idClient);
    OrderEntity findByIdClient(Long idClient);
    List<Order> getAllOrdersByStateEnum(StateEnum stateEnum, Long idRestaurant, int page, int size);
    List<OrderPlate> getAllOrdersByOrder(Order order);
}
