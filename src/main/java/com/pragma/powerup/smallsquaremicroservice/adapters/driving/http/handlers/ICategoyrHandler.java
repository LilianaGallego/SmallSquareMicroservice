package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.CategoryResponseDto;

import java.util.List;

public interface ICategoyrHandler {

    List<CategoryResponseDto> getAllCategories();
}
