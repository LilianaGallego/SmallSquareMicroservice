package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.TraceabilityResponseDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.util.List;
import java.util.Optional;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant, Order order);
    void saveOrderPlate(OrderPlate orderPlate);
    void validateState(OrderEntity orderBD, Order order, Restaurant restaurant);
    void validateRestaurant(OrderEntity orderBD, Long idEmployee);
    void validatePhoneClient(OrderEntity order, int codeClient);
    void validateCodeClient(OrderEntity order, int codeClient);
    List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size);

    List<OrderPlateResponseDto> getAllOrdersByOrder(OrderResponseDto order);
    List<OrderResponseDto> updateStatusOrder(Long idOrder, StateEnum stateEnum, int page, int size);

    void updateOrder(Long idOrder, int codeClient);
    void sendMessageOrder(OrderEntity order, int code);
    int generateCode();

    void validateStateOrder(OrderEntity order, int codeClient);
    void cancelOrder(Long idOrder);

    void validateStateEarning(OrderEntity order);

    void saveRecord(TraceabilityRequestDto traceabilityRequestDto);
    TraceabilityRequestDto saveTraceabilityDto(Optional<OrderEntity> order);

    List<TraceabilityResponseDto> getAllRecordsOrdersByClient();

}
