package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {
    List<Category> getAllCategories();
}
