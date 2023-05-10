package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.UserNotRoleOwnerException;
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
                "18181818",
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
}