package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderPlateEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PageNoValidException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.RestaurantNotExistException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final IRestaurantRepository restaurantRepository;
    private static OrderEntity orderEntity;
    public static OrderEntity getOrderEntity() {
        return orderEntity;
    }

    @Override
    public void saveOrder(Order order) {
        OrderEntity orderBD = orderRepository.findByIdClient(order.getIdClient());
        if(orderBD != null  && (orderBD.getState().equals(StateEnum.READY) || orderBD.getState().equals(StateEnum.PREPARATION) || orderBD.getState().equals(StateEnum.EARNING))){

            throw new OrderInProcessesException();
        }

        order.setState(StateEnum.EARNING);
        orderEntity = orderRepository.save(orderEntityMapper.toEntity(order));
    }

    @Override
    public void saveOrderPlate(OrderPlate orderPlate) {
        orderPlate.setOrder(orderEntityMapper.toOrder(orderEntity));
        orderPlateRepository.save(orderPlateEntityMapper.toEntity(orderPlate));

    }

    @Override
    public List<Order> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size,sort);
        if (page < 0 || page >= pageable.getPageSize()) {
            throw new PageNoValidException();

        }
        Page<OrderEntity> orderEntities = orderRepository.findAllByState(stateEnum,pageable);
        if (orderEntities.isEmpty()) {
            throw new RestaurantNotExistException();
        }
        return orderEntityMapper.toOrderList(orderEntities.getContent());
    }
}
