package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderPlateEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PageNoValidException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Transactional
public class OrderMysqlAdapter implements IOrderPersistencePort {
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderPlateEntityMapper orderPlateEntityMapper;

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
    public OrderEntity findByIdClient(Long idClient) {
        return orderRepository.findByIdClient(idClient);
    }



    @Override
    public List<Order> getAllOrdersByStateEnum(StateEnum state,Long idRestaurant, int page, int size) {


        Pageable pageable = PageRequest.of(page, size);
        if (page < 0 || page >= pageable.getPageSize()) {
            throw new PageNoValidException();

        }

        List<OrderEntity> orderEntities = orderRepository.findAllByStateEnumAndRestaurantEntityId(state.name(),idRestaurant,pageable);
        if (orderEntities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return orderEntityMapper.toOrderList(orderEntities);
    }
}
