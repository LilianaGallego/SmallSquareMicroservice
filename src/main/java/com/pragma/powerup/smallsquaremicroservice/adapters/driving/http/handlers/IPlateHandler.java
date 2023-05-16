package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;

public interface IPlateHandler {
    void savePlate(PlateRequestDto plateRequestDto);
    void updatePlate(Long idPlate, UpdatePlateRequestDto updatePlateRequestDto);

}
