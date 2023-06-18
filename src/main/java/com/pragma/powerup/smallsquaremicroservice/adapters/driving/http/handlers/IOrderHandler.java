package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.util.List;

public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequestDto);
    void saveOrderPlate(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size);
    List<OrderResponseDto> updateStatusOrder(Long idOrder,StateEnum stateEnum, int page, int size);
    void updateOrderReady(Long idOrder);
    void updateOrderDelivered(Long idOrder,int codeClient);
    void cancelOrder(Long idOrder);

}
