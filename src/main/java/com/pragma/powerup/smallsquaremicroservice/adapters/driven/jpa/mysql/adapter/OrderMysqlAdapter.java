package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderPlateEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderPlateEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IOrderPlateResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PageNoValidException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Transactional
public class OrderMysqlAdapter implements IOrderPersistencePort {
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderPlateEntityMapper orderPlateEntityMapper;
    private final IOrderPlateResponseMapper orderPlateResponseMapper;
    private final IOrderResponseMapper orderResponseMapper;

    private final IOrderRepository orderRepository;
    private final IOrderPlateRepository orderPlateRepository;
    private static OrderEntity orderEntity;
    public static OrderEntity getOrderEntity() {
        return orderEntity;
    }

    @Override
    public void saveOrder(Order order) {

        order.setStateEnum(StateEnum.EARNING);
        orderEntity = orderRepository.save(orderEntityMapper.toEntity(order));
    }

    @Override
    public void saveOrderPlate(OrderPlate orderPlate) {
        orderPlate.setOrder(orderEntityMapper.toOrder(orderEntity));
        orderPlateRepository.save(orderPlateEntityMapper.toEntity(orderPlate));

    }

    @Override
    public boolean existsByIdClient(Long idClient) {
        return orderRepository.existsByIdClient(idClient);
    }

    @Override
    public boolean existsById(Long idOrder) {
        return orderRepository.existsById(idOrder);
    }

    @Override
    public OrderEntity findByIdClient(Long idClient) {
        return orderRepository.findByIdClient(idClient);
    }

    @Override
    public Optional<OrderEntity> findById(Long idOrder) {
        return orderRepository.findById(idOrder);
    }


    @Override
    public List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum state, Long idRestaurant, int page, int size) {


        Pageable pageable = PageRequest.of(page, size);
        if (page < 0 || page >= pageable.getPageSize()) {
            throw new PageNoValidException();

        }

        List<OrderEntity> orderEntities = orderRepository.findAllByStateEnumAndRestaurantEntityId(state.name(),idRestaurant,pageable);
        if (orderEntities.isEmpty()) {
            throw new NoDataFoundException();
        }
        List<Order> orders =orderEntityMapper.toOrderList(orderEntities);
        List<OrderResponseDto> orderResponseDtos = orderResponseMapper.toResponseList(orders);
        for (OrderResponseDto order : orderResponseDtos) {
            order.setOrderPlates(getAllOrdersByOrder(order));

        }
        return orderResponseDtos ;
    }

    @Override
    public List<OrderPlateResponseDto> getAllOrdersByOrder(OrderResponseDto order) {
        List<OrderPlateEntity> orderPlateEntities = orderPlateRepository.getAllByOrderEntityId(order.getId());
        return orderPlateResponseMapper.toResponseList(orderPlateEntityMapper.toOrderPlateList(orderPlateEntities));
    }

    @Override
    public List<OrderResponseDto> updateStatusOrder(OrderEntity order, StateEnum stateEnum, Long idRestaurant, int page, int size) {
        orderRepository.save(order);
        return getAllOrdersByStateEnum(stateEnum, idRestaurant, page,size);

    }



}
