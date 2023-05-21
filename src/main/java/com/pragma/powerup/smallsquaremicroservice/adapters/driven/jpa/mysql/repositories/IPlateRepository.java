package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.PlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPlateRepository extends JpaRepository<PlateEntity, Long> {


    List<PlateEntity> findAllByRestaurantEntityId(Long idRestaurant);
}
