package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.PlateEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IPlateEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PageNoValidException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.RestaurantNotExistException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IPlatePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public class PlateMysqlAdapter implements IPlatePersistencePort {
    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper iPlateEntityMapper;
    @Override
    public void savePlate(Plate plate) {

        plateRepository.save(iPlateEntityMapper.toEntity(plate));
    }

    @Override
    public List<Plate> getAllPlatesByRestaurant(Long idRestaurant, Long idCategory, int page, int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size,sort);
        if (page < 0 || page >= pageable.getPageSize()) {
            throw new PageNoValidException();

        }
        Page<PlateEntity> plateEntities = plateRepository.findAllByRestaurantEntityIdAndCategoryEntityId(idRestaurant,idCategory,pageable);
        if (plateEntities.isEmpty()) {
            throw new RestaurantNotExistException();
        }
        return iPlateEntityMapper.toPlateList(plateEntities.getContent());
    }
}
