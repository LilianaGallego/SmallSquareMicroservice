package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;


public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    void validateName(String name);
    void validateAddress(String address);
    void validatePhone(String phone);
    void validateOwner(Long idRestaurant);
    void validateUrlLogo(String urlLogo);
    void validateIdOwner(Long idOwner);
    void validateDniNumber(String dniNumber);
    void saveRestaurantEmployees(Long idEmployee, Long idRestaurant);
    void addEmployee(EmployeeRequestDto employeeRequestDto, Long idRestaurant);


}
