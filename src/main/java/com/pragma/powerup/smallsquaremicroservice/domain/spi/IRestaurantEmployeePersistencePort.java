package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;

public interface IRestaurantEmployeePersistencePort {
    void saveRestaurantEmployee(RestaurantEmployee restaurantEmployee);

}
