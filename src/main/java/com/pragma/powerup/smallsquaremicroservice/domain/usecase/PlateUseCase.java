package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IPlateServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.CategoryNotExistException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.NameRequiredException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PlatePriceNotValidException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.RestaurantNotExistException;
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
        if (plate.getName().equals("")) {
            throw new NameRequiredException();
        }
        Long idRestaurant = plate.getRestaurant().getId();
        Long idCategory = plate.getCategory().getId();

        if (plate.getPrice() <= 0) {
            throw new PlatePriceNotValidException();
        }

        if (!restaurantRepository.existsById(idRestaurant)){
            throw new RestaurantNotExistException();
        }
        if (!categoryRepository.existsById(idCategory)){
            throw new CategoryNotExistException();
        }
        platePersistencePort.savePlate(plate);

    }
}
