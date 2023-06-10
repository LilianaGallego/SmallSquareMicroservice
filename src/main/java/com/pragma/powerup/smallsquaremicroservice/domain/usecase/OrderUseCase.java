package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.time.LocalDate;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort  restaurantPersistencePort;


    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
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
        switch (orderBD.getState()){
            case READY,EARNING,PREPARATION -> throw new OrderInProcessesException();
            case CANCELLED,DELIVERED -> {
                order.setState(StateEnum.EARNING);
                order.setIdClient(TokenInterceptor.getIdUser());
                order.setDate(LocalDate.now());
                order.setRestaurant(restaurant);
                orderPersistencePort.saveOrder(order);
            }
        }
    }


}
