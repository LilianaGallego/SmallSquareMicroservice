package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.OrderPlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.TraceabilityResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IOrderPlateRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderPlateRequestMapper orderPlateRequestMapper;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;

    @Override
    public void saveOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toOrder(orderRequestDto);
        orderServicePort.saveOrder(orderRequestDto.getIdRestaurant(), order);
        saveOrderPlate(orderRequestDto);
    }

    @Override
    public void saveOrderPlate(OrderRequestDto orderRequestDto) {
        List<OrderPlateRequestDto> orderPlates = orderRequestDto.getOrderPlates();

        OrderPlateRequestDto orderPlateRequestDto = new OrderPlateRequestDto();
        for (int i = 0; i < orderPlates.size(); i++) {
               orderPlateRequestDto = orderPlates.get(i);
               orderServicePort.saveOrderPlate(orderPlateRequestMapper.toOrderPlate(orderPlateRequestDto));
        }
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size) {

        return orderServicePort.getAllOrdersByStateEnum(stateEnum, page,size);
    }

    @Override
    public List<OrderResponseDto> updateStatusOrder(Long idOrder, StateEnum stateEnum, int page, int size) {
        orderServicePort.updateStatusOrder(idOrder, stateEnum, page, size);
        return orderServicePort.getAllOrdersByStateEnum(stateEnum, page, size);
    }

    @Override
    public void updateOrderReady(Long idOrder) {
        int codeClient = 0;
        orderServicePort.updateOrder(idOrder, codeClient);
    }

    @Override
    public void updateOrderDelivered(Long idOrder, int codeClient) {
        orderServicePort.updateOrder(idOrder, codeClient);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        orderServicePort.cancelOrder(idOrder);
    }

    @Override
    public List<TraceabilityResponseDto> getAllRecordsOrdersByClient() {
        return orderServicePort.getAllRecordsOrdersByClient();
    }

}
