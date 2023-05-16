package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryPersistencePort.getAll();
    }
}
