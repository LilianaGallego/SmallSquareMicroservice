package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;

import java.util.List;

public interface IPlatePersistencePort {
    void savePlate(Plate plate);
    List<Plate> getAllPlatesByRestaurant(Long idRestaurant, Long idCategory, int page, int size);
}
