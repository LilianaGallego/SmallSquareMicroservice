package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.OrderRequestDto;

public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequestDto);
    void saveOrderPlate(OrderRequestDto orderRequestDto);
}
