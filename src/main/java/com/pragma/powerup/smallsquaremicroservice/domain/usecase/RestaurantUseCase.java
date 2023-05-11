package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.UserNotRoleOwnerException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import org.springframework.beans.factory.annotation.Autowired;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    @Autowired
    protected OwnerHttpAdapter ownerHttpAdapter;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        Long id = restaurant.getIdOwner();

        User user = ownerHttpAdapter.getOwner(id);

        if (!user.getIdRole().equals(Constants.OWNER_ROLE_ID) ){
            throw new UserNotRoleOwnerException();
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }


}
