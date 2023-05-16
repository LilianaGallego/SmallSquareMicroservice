package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;

public interface IPlatePersistencePort {
    void savePlate(Plate plate);
}
