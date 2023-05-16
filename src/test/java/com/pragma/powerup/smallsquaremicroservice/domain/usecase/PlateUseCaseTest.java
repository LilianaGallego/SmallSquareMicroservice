package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.DescriptionRequiredException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.NameRequiredException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PlatePriceNotValidException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.UrlImageRequiredException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IPlatePersistencePort;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PlateUseCaseTest {

    @Mock
    private IPlatePersistencePort platePersistencePort;
    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private ICategoryRepository categoryRepository;

    private PlateUseCase plateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        plateUseCase = new PlateUseCase(platePersistencePort, restaurantRepository, categoryRepository);
    }

    @Test
    void testSavePlate() {
        // Arrange

        Restaurant restaurant = new Restaurant(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");
        Category category = new Category(1L,"Entrada","Papitas chips");

        Plate plate = new Plate(10L,"papitas chip",1000,"crocantes papitas chip 100gr","urlimage",category,true, restaurant);

        Mockito.when(restaurantRepository.existsById(10L)).thenReturn(true);
        Mockito.when(categoryRepository.existsById(1L)).thenReturn(true);
        // Act
        plateUseCase.savePlate(plate);

        // Assert
        Mockito.verify(platePersistencePort, times(1)).savePlate(plate);

    }

    @Test
    void validateName_ValidName_NoExceptionThrown() {
        // Arrange
        String validName = "papitas chip";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            plateUseCase.validateName(validName);
        });
    }

    @Test
    void validateName_EmptyName_ThrowsNameRequiredException() {
        // Arrange
        String emptyName = "";

        // Act & Assert
        Assertions.assertThrows(NameRequiredException.class, () -> {
            plateUseCase.validateName(emptyName);
        });
    }

    @Test
    void validatePrice_ValidPrice_NoExceptionThrown() {
        // Arrange
        int price = 100;

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            plateUseCase.validatePrice(price);
        });
    }

    @Test
    void validatePrice_EmptyPrice_ThrowsNameRequiredException() {
        // Arrange
        int badPrice = 0;

        // Act & Assert
        Assertions.assertThrows(PlatePriceNotValidException.class, () -> {
            plateUseCase.validatePrice(badPrice);
        });
    }


    @Test
    void validateDescription_validateDescription_NoExceptionThrown() {
        // Arrange
        String validateDescription = "papitas chip";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            plateUseCase.validateDescription(validateDescription);
        });
    }

    @Test
    void validateDescription_EmptyDescription_ThrowsNameRequiredException() {
        // Arrange
        String emptyDescription = "";

        // Act & Assert
        Assertions.assertThrows(DescriptionRequiredException.class, () -> {
            plateUseCase.validateDescription(emptyDescription);
        });
    }

    @Test
    void validateUrlImage_validateUrlImage_NoExceptionThrown() {
        // Arrange
        String validateUrlImage = "url";

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            plateUseCase.validateUrlImage(validateUrlImage);
        });
    }

    @Test
    void validateUrlImage_EmptyUrlImage_ThrowsNameRequiredException() {
        // Arrange
        String emptyUrlImage = "";

        // Act & Assert
        Assertions.assertThrows(UrlImageRequiredException.class, () -> {
            plateUseCase.validateUrlImage(emptyUrlImage);
        });
    }

    @Test
    void validateRestaurantId() {
    }

    @Test
    void validateCategoryId() {
    }
}