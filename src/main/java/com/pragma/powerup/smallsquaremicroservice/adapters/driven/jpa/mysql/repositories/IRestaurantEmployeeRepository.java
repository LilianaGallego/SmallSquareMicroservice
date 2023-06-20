package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity, Long> {
    RestaurantEmployeeEntity getRestaurantEmployeeByIdEmployee(Long idEmployee);
    
}
