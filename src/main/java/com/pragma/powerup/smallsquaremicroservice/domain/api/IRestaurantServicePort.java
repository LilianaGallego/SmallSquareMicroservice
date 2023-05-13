package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    void validateName(String name);
    void validateAddress(String address);
    void validatePhone(String phone);
    void validateUrlLogo(String urlLogo);
    void validateIdOwner(Long idOwner);
    void validateDniNumber(String dniNumber);


}
