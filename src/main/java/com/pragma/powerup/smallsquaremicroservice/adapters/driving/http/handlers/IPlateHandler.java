package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.PlateResponseDto;

import java.util.List;

public interface IPlateHandler {
    void savePlate(PlateRequestDto plateRequestDto);
    void updatePlate(Long idPlate, UpdatePlateRequestDto updatePlateRequestDto);

    void updateStatusPlate(Long idPlate);

    List<PlateResponseDto> getAllPlatesByRestaurant(Long idRestaurant,Long idCategory, int page, int size);

}
