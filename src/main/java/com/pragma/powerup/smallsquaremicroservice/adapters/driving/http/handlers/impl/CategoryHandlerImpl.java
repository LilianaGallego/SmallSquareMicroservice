package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.CategoryResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.ICategoyrHandler;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryHandlerImpl implements ICategoyrHandler {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryResponseMapper.toResponseList(categoryServicePort.getAllCategories());
    }
}
