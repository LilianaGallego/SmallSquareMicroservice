package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IPlateServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.*;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IPlatePersistencePort;

public class PlateUseCase  implements IPlateServicePort {
    private final IPlatePersistencePort platePersistencePort;
    private final IRestaurantRepository restaurantRepository;
    private final ICategoryRepository categoryRepository;

    public PlateUseCase(IPlatePersistencePort platePersistencePort, IRestaurantRepository restaurantRepository, ICategoryRepository categoryRepository) {
        this.platePersistencePort = platePersistencePort;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void savePlate(Plate plate) {
        plate.setActive(true);

        validateName(plate.getName());
        validatePrice(plate.getPrice());
        validateDescription(plate.getDescription());
        validateUrlImage(plate.getUrlImage());
        validateRestaurantId(plate.getRestaurant().getId());
        validateCategoryId(plate.getCategory().getId());
        platePersistencePort.savePlate(plate);

    }

    @Override
    public void validateName(String name) {
        if (name.equals("")) {
            throw new NameRequiredException();
        }
    }

    @Override
    public void validatePrice(int price) {

        if (price <= 0) {
            throw new PlatePriceNotValidException();
        }
    }

    @Override
    public void validateDescription(String description) {

        if (description.equals("")) {
            throw new DescriptionRequiredException();
        }
    }

    @Override
    public void validateUrlImage(String urlImage) {

        if (urlImage.equals("")) {
            throw new UrlImageRequiredException();
        }
    }


    @Override
    public void validateRestaurantId(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotExistException();
        }
    }

    @Override
    public void validateCategoryId(Long categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotExistException();
        }
    }

}
