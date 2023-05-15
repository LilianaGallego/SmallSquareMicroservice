package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;

public interface IPlateServicePort {

    void savePlate(Plate plate);
}
