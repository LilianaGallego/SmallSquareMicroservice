package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.PlateEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.PlateAlreadyExistsException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IPlateServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.*;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IPlatePersistencePort;

import java.util.List;
import java.util.Optional;

public class PlateUseCase  implements IPlateServicePort {
    private final IPlatePersistencePort platePersistencePort;
    private final IRestaurantRepository restaurantRepository;
    private final ICategoryRepository categoryRepository;
    private final IPlateRepository plateRepository;

    public PlateUseCase(IPlatePersistencePort platePersistencePort, IPlateRepository plateRepository, IRestaurantRepository restaurantRepository, ICategoryRepository categoryRepository) {
        this.platePersistencePort = platePersistencePort;
        this.plateRepository = plateRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void savePlate(Plate plate) {
        plate.setActive(true);
        validateIdOwner(plate.getRestaurant().getId());
        validateName(plate.getName());
        validatePrice(plate.getPrice());
        validateDescription(plate.getDescription());
        validateUrlImage(plate.getUrlImage());
        validateRestaurantId(plate.getRestaurant().getId(),plate);
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
    public void validateRestaurantId(Long restaurantId, Plate plate) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotExistException();
        }

        List<PlateEntity> dishes = plateRepository.findAllByRestaurantEntityId(restaurantId);
        dishes.forEach(d -> {
            if(d.getName().equals(plate.getName())){
                throw new PlateAlreadyExistsException();
            }
        });

    }

    @Override
    public void validateCategoryId(Long categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotExistException();
        }
    }


    @Override
    public void updatePlate(Long idPlate, UpdatePlateRequestDto updatePlateRequestDto) {
        PlateEntity plate = plateRepository.findById(idPlate)
                .orElseThrow(PlateNotExistException::new);
        validateIdOwner(plate.getRestaurantEntity().getId());
        if(plateRepository.findById(idPlate).isPresent()){

            plate.setDescription(updatePlateRequestDto.getDescription());
            plate.setPrice(updatePlateRequestDto.getPrice());

            plateRepository.save(plate);
        }

    }
    @Override
    public void validateIdOwner(Long idRestaurant) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(idRestaurant);

        Long idOwnerToken = TokenInterceptor.getIdOwner();
        RestaurantEntity restaurantEntity = restaurantEntityOptional.get();
        if (!restaurantEntity.getIdOwner().equals(idOwnerToken)) {
            throw new NotOwnerRestaurant();
        }
    }

}
