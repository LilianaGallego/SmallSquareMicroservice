package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;

public interface IPlateHandler {
    void savePlate(PlateRequestDto plateRequestDto);

}
