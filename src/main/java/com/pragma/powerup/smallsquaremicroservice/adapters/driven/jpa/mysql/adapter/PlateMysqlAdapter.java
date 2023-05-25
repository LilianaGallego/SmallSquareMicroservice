package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.PlateAlreadyExistsException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IPlateEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IPlatePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class PlateMysqlAdapter implements IPlatePersistencePort {
    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper iPlateEntityMapper;
    @Override
    public void savePlate(Plate plate) {
        if(!plateRepository.findById(plate.getId()).isPresent()){
            throw new PlateAlreadyExistsException();
        }

        plateRepository.save(iPlateEntityMapper.toEntity(plate));
    }
}
