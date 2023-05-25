package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.PlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPlateRepository extends JpaRepository<PlateEntity, Long> {

    Optional<PlateEntity> findByName(String name);
    List<PlateEntity> findAllByRestaurantEntityId(Long idRestaurant);
}
