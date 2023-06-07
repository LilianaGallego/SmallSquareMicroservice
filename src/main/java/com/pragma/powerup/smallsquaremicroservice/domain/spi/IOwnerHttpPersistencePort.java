package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;

public interface IOwnerHttpPersistencePort {
    User getOwner(Long id);
}
