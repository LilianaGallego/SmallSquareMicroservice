package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;

public interface IEmployeePersistencePort {
    void createEmployee(EmployeeRequestDto user, Long idRestaurant);
    User getEmployee(String dni);
}
