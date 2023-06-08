package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderPlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderPlateRepository extends JpaRepository<OrderPlateEntity, Long> {
}
