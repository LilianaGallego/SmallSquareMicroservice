package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;

public interface IPlateServicePort {

    void savePlate(Plate plate);
    void validateName(String name);
    void validatePrice(int price);
    void validateRestaurantId(Long restaurantId);
    void validateCategoryId(Long categoryId);
    void validateDescription(String description);
    void validateUrlImage(String urlLogo);

    void updatePlate(Long idPlate, UpdatePlateRequestDto updatePlateRequestDto);
}
