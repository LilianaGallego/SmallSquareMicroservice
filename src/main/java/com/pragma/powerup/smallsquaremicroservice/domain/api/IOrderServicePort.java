package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant, Order order);
    void saveOrderPlate(OrderPlate orderPlate);

}
