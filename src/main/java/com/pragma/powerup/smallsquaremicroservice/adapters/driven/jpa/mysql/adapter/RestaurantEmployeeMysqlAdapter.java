package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantEmployeeRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class RestaurantEmployeeMysqlAdapter implements IRestaurantEmployeePersistencePort {
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Override
    public void saveRestaurantEmployee(RestaurantEmployee restaurantEmployee) {
        restaurantEmployeeRepository.save(restaurantEmployeeEntityMapper.toEntity(restaurantEmployee));
    }

    @Override
    public RestaurantEmployee getRestaurantEmployeeByIdEmployee(Long idEmployee) {
        RestaurantEmployeeEntity restaurantEmployeeEntity = restaurantEmployeeRepository.getRestaurantEmployeeByIdEmployee(idEmployee);
         return restaurantEmployeeEntityMapper.toRestaurantEmployee(restaurantEmployeeEntity);
    }

}
