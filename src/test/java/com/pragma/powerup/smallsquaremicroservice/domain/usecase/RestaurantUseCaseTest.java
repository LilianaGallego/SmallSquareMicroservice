package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.*;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@SuppressWarnings("ALL")
@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private OwnerHttpAdapter ownerHttpAdapter;
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRestaurant()  {
        // Arrange
        User user = new User("Lili", "Gallego","lili@gmail.com","288383",
                new Date(1989, 3, 4),"12345","123456",Constants.OWNER_ROLE_ID);


        Restaurant restaurant = new Restaurant(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "573118688145",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");


        Mockito.when(ownerHttpAdapter.getOwner((10L))).thenReturn(user);
        restaurantPersistencePort.saveRestaurant(restaurant);
        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        Mockito.verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_invalidRoleUser() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(123L);// Se pasa el id de un usuario que no tiene rol de propietario

        User user = new User();
        user.setIdRole(456L); // Rol incorrecto

        Mockito.when(ownerHttpAdapter.getOwner(123L)).thenReturn(user);

        try {
            // Act
            restaurantUseCase.saveRestaurant(restaurant);
            fail("Expected UserNotRoleOwnerException to be thrown");
        } catch (UserNotRoleOwnerException ex) {
            // Assert
            // Verifica que se haya lanzado la excepción correctamente
            Assertions.assertEquals("UserNotRoleOwnerException", ex.getClass().getSimpleName());
        }

        // Verifica que no se haya llamado al método saveRestaurant
        Mockito.verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
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
}





