package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.PlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IPlateHandler;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IPlateRequestMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IPlateResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IPlateServicePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateHandlerImpl implements IPlateHandler {
    private final IPlateServicePort  plateServicePort;
    private final IPlateRequestMapper plateRequestMapper;
    private final IPlateResponseMapper plateResponseMapper;

    public PlateHandlerImpl(IPlateServicePort plateServicePort, IPlateRequestMapper plateRequestMapper, IPlateResponseMapper plateResponseMapper) {
        this.plateServicePort = plateServicePort;
        this.plateRequestMapper = plateRequestMapper;
        this.plateResponseMapper = plateResponseMapper;
    }

    @Override
    public void savePlate(PlateRequestDto plateRequestDto) {

        plateServicePort.savePlate(plateRequestMapper.toPlate(plateRequestDto));
    }

    @Override
    public void updatePlate(Long idPlate, UpdatePlateRequestDto updatePlateRequestDto) {
        plateServicePort.updatePlate(idPlate, updatePlateRequestDto);
    }

    @Override
    public void updateStatusPlate(Long idPlate) {
        plateServicePort.updateStatusPlate(idPlate);
    }

    @Override
    public List<PlateResponseDto> getAllPlatesByRestaurant(Long idRestaurant, Long idCategory, int page, int size) {
        return plateResponseMapper.toResponseList(plateServicePort.getAllPlatesByRestaurant(idRestaurant, idCategory, page,size));
    }
}
