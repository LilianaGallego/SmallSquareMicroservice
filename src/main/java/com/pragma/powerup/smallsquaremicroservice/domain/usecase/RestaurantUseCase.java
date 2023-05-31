package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.*;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    @Autowired
    protected OwnerHttpAdapter ownerHttpAdapter;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        Long id = restaurant.getIdOwner();

        User user = ownerHttpAdapter.getOwner(id);
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
    public List<Restaurant> getAllRestaurants(int page, int pageSize) {
        if (page>0){
            List<Restaurant> restaurants = restaurantPersistencePort.getAllRestaurants(page, pageSize);
            restaurants.sort(Comparator.comparing(Restaurant::getName));
            return  Pagination.paginate(restaurants, pageSize, page);
        }
        throw new PageNoValidException();

    }


}
