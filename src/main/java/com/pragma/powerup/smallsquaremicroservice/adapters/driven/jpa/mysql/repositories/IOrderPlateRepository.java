package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderPlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderPlateRepository extends JpaRepository<OrderPlateEntity, Long> {
    List<OrderPlateEntity> getAllByOrderEntityId(Long idOrder);
}
