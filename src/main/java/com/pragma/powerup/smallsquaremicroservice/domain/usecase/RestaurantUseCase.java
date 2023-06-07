package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.*;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOwnerHttpPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IEmployeePersistencePort employeePersistencePort;

    private final IRestaurantRepository restaurantRepository;

    private final IOwnerHttpPersistencePort ownerHttpPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IEmployeePersistencePort employeePersistencePort, IRestaurantRepository restaurantRepository, IOwnerHttpPersistencePort ownerHttpPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.employeePersistencePort = employeePersistencePort;
        this.restaurantRepository = restaurantRepository;
        this.ownerHttpPersistencePort = ownerHttpPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        Long id = restaurant.getIdOwner();

        User user = ownerHttpPersistencePort.getOwner(id);
        if (!user.getIdRole().equals(Constants.OWNER_ROLE_ID) ){
            throw new UserNotRoleOwnerException();
        }
        validateName(restaurant.getName());
        validateAddress(restaurant.getAddress());
        validatePhone(restaurant.getPhone());
        validateUrlLogo(restaurant.getUrlLogo());
        validateIdOwner(restaurant.getIdOwner());
        validateDniNumber(restaurant.getDniNumber());

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public void validateName(String name) {
        String regex = "^(?=.*[a-zA-Z\\s])[a-zA-Z\\s0-9]+$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(name).matches()|| name.equals("")){

            throw new NameRequiredException();
        }
    }

    @Override
    public void validateAddress(String address) {
        if (address.equals("")){
            throw new AddressRequiredException();
        }
    }

    @Override
    public void validatePhone(String phone) {
        String regex = "^\\+?[0-9]{12}$";

        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(phone).matches() || phone.equals("")) {
            throw new PhoneRequiredException();
        }
    }

    @Override
    public void validateUrlLogo(String urlLogo) {
        if (urlLogo.equals("")) {
            throw new UrlLogoRequiredException();
        }
    }

    @Override
    public void validateIdOwner(Long idOwner) {
        if (idOwner.equals(0L)){
            throw new IdOwnerRequiredException();
        }
    }

    @Override
    public void validateDniNumber(String dniNumber) {
        String regex = "^[0-9]{1,20}$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(dniNumber).matches()||dniNumber.equals("")) {
            throw new DniNumberRequiredException();
        }

    }

    @Override
    public void saveRestaurantEmployees(Long idEmployee, Long idRestaurant) {

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, idRestaurant);
        restaurantEmployeePersistencePort.saveRestaurantEmployee(restaurantEmployee);
    }

    @Override
    public void addEmployee(EmployeeRequestDto user, Long idRestaurant) {
        validateOwner(idRestaurant);
        employeePersistencePort.createEmployee(user, idRestaurant);
        User user1 = employeePersistencePort.getEmployee(user.getDniNumber());
        saveRestaurantEmployees(user1.getId(),idRestaurant);

    }

    @Override
    public void validateOwner(Long idRestaurant) {
        Optional<RestaurantEntity> restaurantEntityOptional ;
        if(restaurantRepository.findById(idRestaurant).isEmpty()){
            throw new RestaurantNotExistException();
        }
        restaurantEntityOptional = restaurantRepository.findById(idRestaurant);
        Long idOwnerToken = TokenInterceptor.getIdOwner();
        RestaurantEntity restaurantEntity = restaurantEntityOptional.get();
        if (!restaurantEntity.getIdOwner().equals(idOwnerToken)) {
            throw new NotOwnerRestaurant();
        }
    }

    @Override
    public List<Restaurant> getAllRestaurants(int page, int pageSize) {
        return restaurantPersistencePort.getAllRestaurants(page, pageSize);

    }


}
