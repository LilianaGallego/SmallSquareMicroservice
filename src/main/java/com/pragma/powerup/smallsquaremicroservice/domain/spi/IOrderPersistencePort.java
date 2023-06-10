package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.util.List;

public interface IOrderPersistencePort {

    void saveOrder(Order order);
    void saveOrderPlate(OrderPlate orderPlate);
    List<Order> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size);
}
