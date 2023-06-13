package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {

    void saveOrder(Order order);
    void saveOrderPlate(OrderPlate orderPlate);
    boolean existsByIdClient(Long idClient);
    boolean existsById(Long idOrder);
    OrderEntity findByIdClient(Long idClient);

    Optional<OrderEntity> findById(Long idOrder);
    List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum stateEnum, Long idRestaurant, int page, int size);
    List<OrderPlateResponseDto> getAllOrdersByOrder(OrderResponseDto order);
    List<OrderResponseDto> updateStatusOrder(OrderEntity order, StateEnum stateEnum, Long idRestaurant, int page, int size);
}
