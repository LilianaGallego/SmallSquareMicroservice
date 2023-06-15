package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;

public interface IUserHttpPersistencePort {
    User getOwner(Long id);
    User getClient(Long id);
    User getuser(String urlId);
}
