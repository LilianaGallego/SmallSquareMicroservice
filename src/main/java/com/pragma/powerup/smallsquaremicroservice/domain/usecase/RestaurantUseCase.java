package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller.OwnerRestController;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.RestaurantNotCreatedException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.UserNotRoleOwnerException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    @Autowired
    private OwnerRestController restController;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        Long id = restaurant.getIdOwner();
        String name;

        User user = restController.createRestaurant(id);
        System.out.println(user.getIdRole());
        if (user.getIdRole()!= Constants.OWNER_ROLE_ID){
            throw new UserNotRoleOwnerException();
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }


}
