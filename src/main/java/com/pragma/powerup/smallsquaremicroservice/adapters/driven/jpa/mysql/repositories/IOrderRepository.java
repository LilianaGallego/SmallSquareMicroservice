package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByIdClient(Long idClient);
    boolean existsByIdClient(Long idClient);
    boolean existsById(Long id);
   List<OrderEntity> findAllByStateEnumAndRestaurantEntityId(String stateEnum, Long idRestaurant, Pageable pageable);

}
