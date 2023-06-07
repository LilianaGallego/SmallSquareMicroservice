package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("ALL")
@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IRestaurantEntityMapper entityMapper;
    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IEmployeePersistencePort employeePersistencePort;
    @Mock
    private IOwnerHttpPersistencePort  ownerHttpPersistencePort;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, restaurantEmployeePersistencePort,
                employeePersistencePort,restaurantRepository, ownerHttpPersistencePort);
    }

    @Test
    void testSaveRestaurant()  {
        // Arrange
        User user = new User(1L,"Lili", "Gallego","lili@gmail.com","288383",
                LocalDate.of(1989, 3, 4),"12345","123456",Constants.OWNER_ROLE_ID);


        Restaurant restaurant = new Restaurant(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "573118688145",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");


       when(ownerHttpPersistencePort.getOwner((10L))).thenReturn(user);
        restaurantPersistencePort.saveRestaurant(restaurant);
        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        verify(restaurantPersistencePort, times(2)).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_invalidRoleUser() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(123L);// Se pasa el id de un usuario que no tiene rol de propietario

        User user = new User();
        user.setIdRole(456L); // Rol incorrecto

        Mockito.when(ownerHttpPersistencePort.getOwner(123L)).thenReturn(user);


        verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
        Assertions.assertThrows(UserNotRoleOwnerException.class, () -> {
            restaurantUseCase.saveRestaurant(restaurant);
        });
    }

    @Test
    void validateName_ValidName_NoExceptionThrown() {
        // Arrange
        String validName = "Restaurant ABC";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.validateName(validName);
        });
    }

    @Test
    void validateName_EmptyName_ThrowsNameRequiredException() {
        // Arrange
        String emptyName = "";

        // Act & Assert
        Assertions.assertThrows(NameRequiredException.class, () -> {
            restaurantUseCase.validateName(emptyName);
        });
    }

    @Test
    void validateName_InvalidName_ThrowsNameRequiredException() {
        // Arrange
        String invalidName = "123";

        // Act & Assert
        Assertions.assertThrows(NameRequiredException.class, () -> {
            restaurantUseCase.validateName(invalidName);
        });
    }

    @Test
    void validateAddress_ValidAddress_NoExceptionThrown() {
        // Arrange
        String validAddress = "direccion";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.validateAddress(validAddress);
        });
    }

    @Test
    void validateAddress_EmptyAddress_ThrowsAddressRequiredException() {
        // Arrange
        String emptyAddress = "";

        // Act & Assert
        Assertions.assertThrows(AddressRequiredException.class, () -> {
            restaurantUseCase.validateAddress(emptyAddress);
        });
    }

    @Test
    void validatePhone_ValidPhone_NoExceptionThrown() {
        // Arrange
        String validPhone = "+573118688145";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.validatePhone(validPhone);
        });
    }

    @Test
    void validatePhone_EmptyPhone_ThrowsPhoneRequiredException() {
        // Arrange
        String emptyPhone = "";

        // Act & Assert
        Assertions.assertThrows(PhoneRequiredException.class, () -> {
            restaurantUseCase.validatePhone(emptyPhone);
        });
    }

    @Test
    void validatePhone_InvalidPhone_ThrowsPhoneRequiredException() {
        // Arrange
        String invalidPhone = "123";

        // Act & Assert
        Assertions.assertThrows(PhoneRequiredException.class, () -> {
            restaurantUseCase.validatePhone(invalidPhone);
        });
    }

    @Test
    void validateUrlLogo_ValidUrlLogo_NoExceptionThrown() {
        // Arrange
        String validUrlLogo = "urllogo.com";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.validateUrlLogo(validUrlLogo);
        });
    }

    @Test
    void validateUrlLogo_EmptyUrl_ThrowsUrlLogoRequiredException() {
        // Arrange
        String emptyUrl = "";

        // Act & Assert
        Assertions.assertThrows(UrlLogoRequiredException.class, () -> {
            restaurantUseCase.validateUrlLogo(emptyUrl);
        });
    }

    @Test
    void validateIdOwner_ValidId_NoExceptionThrown() {
        // Arrange
        Long validId = 123L;

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.validateIdOwner(validId);
        });
    }

    @Test
    void validateIdOwner_ZeroId_ThrowsIdOwnerRequiredException() {
        // Arrange
        Long zeroId = 0L;

        // Act & Assert
        Assertions.assertThrows(IdOwnerRequiredException.class, () -> {
            restaurantUseCase.validateIdOwner(zeroId);
        });
    }

    @Test
    void validateDniNumber_ValidPDniNumber_NoExceptionThrown() {
        // Arrange
        String validDniNumber = "3115";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.validateDniNumber(validDniNumber);
        });
    }

    @Test
    void validateDniNumber_EmptyDniNumber_ThrowsDniNumberRequiredException() {
        // Arrange
        String emptyDniNumber = "";

        // Act & Assert
        Assertions.assertThrows(DniNumberRequiredException.class, () -> {
            restaurantUseCase.validateDniNumber(emptyDniNumber);
        });
    }

    @Test
    void testSaveRestaurantEmployees() {
        // Arrange
        Long idEmployee = 1L;
        Long idRestaurant = 2L;

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee(idEmployee, idRestaurant);
        // Act
        restaurantUseCase.saveRestaurantEmployees(restaurantEmployee.getIdEmployee(), restaurantEmployee.getIdRestaurant());

        // Assert
        verify(restaurantEmployeePersistencePort, times(1)).saveRestaurantEmployee(Mockito.any(RestaurantEmployee.class));
    }

    @Test
    void testAddEmployee() {
        // Arrange
        Long idEmployee = 1L;
        Long idRestaurant = 2L;
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setDniNumber("12345678");
        User user = new User();
        user.setId(idEmployee);
        user.setDniNumber(employeeRequestDto.getDniNumber());
        Restaurant restaurant = new Restaurant(2L, "Name", "Address", "56165", "urlLogo.jpg",
                2L, "1235156");
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        TokenInterceptor.setIdOwner(2L);
        restaurantEntity.setIdOwner(TokenInterceptor.getIdOwner());

        employeePersistencePort.createEmployee(employeeRequestDto,idRestaurant);
        when(employeePersistencePort.getEmployee(employeeRequestDto.getDniNumber())).thenReturn(user);
        when(restaurantRepository.findById(idRestaurant)).thenReturn(Optional.of(restaurantEntity));


        // Act
        restaurantUseCase.addEmployee(employeeRequestDto, idRestaurant);

        // Assert
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.addEmployee(employeeRequestDto,idRestaurant);
        });
        verify(employeePersistencePort, times(3)).createEmployee(employeeRequestDto, idRestaurant);
        verify(employeePersistencePort, times(2)).getEmployee("12345678");
        verify(restaurantEmployeePersistencePort, times(2)).saveRestaurantEmployee(Mockito.any(RestaurantEmployee.class));
    }

    @Test
    void testGetAllRestaurants() {
        // Arrange
        int page = 1;
        int size = 10;


        List<Restaurant> restaurants = new LinkedList<>();
        restaurants.add(new Restaurant(
                10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "573118688145",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919"));


        Mockito.when(restaurantPersistencePort.getAllRestaurants(1, 10)).thenReturn(restaurants);

        // Act
        List<Restaurant> result = restaurantUseCase.getAllRestaurants(page, size);

        // Assert
        verify(restaurantPersistencePort).getAllRestaurants(1, 10);
        Assertions.assertDoesNotThrow(() -> {
            restaurantUseCase.getAllRestaurants(page, size);});

    }
}
