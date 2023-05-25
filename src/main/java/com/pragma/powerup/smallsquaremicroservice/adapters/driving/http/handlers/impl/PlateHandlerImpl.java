package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IPlateHandler;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IPlateRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IPlateServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlateHandlerImpl implements IPlateHandler {
    private final IPlateServicePort  plateServicePort;
    private final IPlateRequestMapper plateRequestMapper;

    @Override
    public void savePlate(PlateRequestDto plateRequestDto) {

        plateServicePort.savePlate(plateRequestMapper.toPlate(plateRequestDto));
    }
}
