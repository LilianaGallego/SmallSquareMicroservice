package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByIdClient(Long idClient);
}
