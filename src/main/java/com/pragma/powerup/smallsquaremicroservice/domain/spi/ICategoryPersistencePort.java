package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    List<Category> getAll();
}
