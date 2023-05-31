package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.PlateEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.*;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Plate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IPlatePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlateUseCaseTest {

    @Mock
    private IPlatePersistencePort platePersistencePort;
    @Mock
    private IPlateRepository plateRepository;
    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private ICategoryRepository categoryRepository;

    private PlateUseCase plateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        plateUseCase = new PlateUseCase(platePersistencePort, plateRepository,restaurantRepository, categoryRepository);
    }

    @Test
    void saveDish_WhenNewPlateWithValidOwner_ShouldSavePlateInRepository() {
        // Arrange
        Plate plate = new Plate(1L, "Name", 1000, "descripcion", "url", new Category(),
                true, new Restaurant(1L, "Name", "Address", "56165", "urlLogo.jpg",
                2L, "1235156"));

        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "Name", "Address", "56165",
                "urlLogo.jpg", 2L, "1235156");
        TokenInterceptor.setIdOwner(2L);
        restaurantEntity.setIdOwner(TokenInterceptor.getIdOwner());
        when(restaurantRepository.existsById(plate.getRestaurant().getId())).thenReturn(true);
        when(restaurantRepository.findById(plate.getRestaurant().getId())).thenReturn(Optional.of(restaurantEntity));
        when(categoryRepository.existsById(plate.getCategory().getId())).thenReturn(true);
        // Act
        plateUseCase.savePlate(plate);

        // Assert
        verify(restaurantRepository, atLeastOnce()).findById(plate.getRestaurant().getId());
        verify(platePersistencePort).savePlate(plate);
    }

    @Test
    void saveDish_WhenNewPlateWithInvalidRestaurant_ThrowRestaurantNotExistException() {
        // Arrange
        Plate plate = new Plate(1L, "Name", 1000, "descripcion", "url", new Category(),
                true, new Restaurant(1L, "Name", "Address", "56165", "urlLogo.jpg",
                2L, "1235156"));

        TokenInterceptor.setIdOwner(2L);

        // Act & Assert
        assertThrows(RestaurantNotExistException.class, () -> plateUseCase.savePlate(plate));
    }

    @Test
    void saveDish_WhenNewDishWithInvalidCategory_ThrowCategoryNotExistException() {
        // Arrange
        Plate plate = new Plate(1L, "Name", 1000, "descripcion", "url", new Category(1L, "CategoryName","descripcion"),
                true, new Restaurant(1L, "Name", "Address", "56165", "urlLogo.jpg",
                2L, "1235156"));

        TokenInterceptor.setIdOwner(2L);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "Name", "Address", "56165",
                "urlLogo.jpg", 2L, "1235156");

        when(restaurantRepository.existsById(plate.getRestaurant().getId())).thenReturn(true);
        when(restaurantRepository.findById(plate.getRestaurant().getId())).thenReturn(Optional.of(restaurantEntity));
        when(categoryRepository.existsById(plate.getCategory().getId())).thenReturn(false);

        // Act & Assert
        assertThrows(CategoryNotExistException.class, () -> plateUseCase.savePlate(plate));
    }

    @Test
    void validateName_ValidName_NoExceptionThrown() {
        // Arrange
        String validName = "papitas chip";

        // Act & Assert
        assertDoesNotThrow(() -> plateUseCase.validateName(validName));
    }

    @Test
    void validateName_EmptyName_ThrowsNameRequiredException() {
        // Arrange
        String emptyName = "";

        // Act & Assert
        assertThrows(NameRequiredException.class, () -> plateUseCase.validateName(emptyName));
    }

    @Test
    void validatePrice_ValidPrice_NoExceptionThrown() {
        // Arrange
        int price = 100;

        // Act & Assert
        assertDoesNotThrow(() -> plateUseCase.validatePrice(price));
    }

    @Test
    void validatePrice_EmptyPrice_ThrowsPriceRequiredException() {
        // Arrange
        int badPrice = 0;

        // Act & Assert
        assertThrows(PlatePriceNotValidException.class, () -> plateUseCase.validatePrice(badPrice));
    }


    @Test
    void validateDescription_validateDescription_NoExceptionThrown() {
        // Arrange
        String validateDescription = "papitas chip";

        // Act & Assert
        assertDoesNotThrow(() -> plateUseCase.validateDescription(validateDescription));
    }

    @Test
    void validateDescription_EmptyDescription_ThrowsDescriptioneRequiredException() {
        // Arrange
        String emptyDescription = "";

        // Act & Assert
        assertThrows(DescriptionRequiredException.class, () -> plateUseCase.validateDescription(emptyDescription));
    }

    @Test
    void validateUrlImage_validateUrlImage_NoExceptionThrown() {
        // Arrange
        String validateUrlImage = "url";

        // Act & Assert
        assertDoesNotThrow(() -> plateUseCase.validateUrlImage(validateUrlImage));
    }

    @Test
    void validateUrlImage_EmptyUrlImage_ThrowsUrlRequiredException() {
        // Arrange
        String emptyUrlImage = "";

        // Act & Assert
        assertThrows(UrlImageRequiredException.class, () -> plateUseCase.validateUrlImage(emptyUrlImage));
    }

    @Test
    void testValidateRestaurantId_ExistingRestaurant_ShouldNotThrowException() {
        // Arrange
        Category category = new Category(1L,"Entrada","Papitas chips");
        Long restaurantId = 1L;
        Plate plate = new Plate();
        plate.setName("New Plate");
        plate.setCategory(category);

        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(plateRepository.findAllByRestaurantEntityId(restaurantId)).thenReturn(new ArrayList<>());

        // Act
        plateUseCase.validateRestaurantId(restaurantId, plate);

        // Assert
        assertDoesNotThrow(() -> plateUseCase.validateRestaurantId(restaurantId,plate));
    }

    @Test
    void testValidateRestaurantId_NonExistingRestaurant_ShouldThrowRestaurantNotExistException() {
        // Arrange
        Restaurant restaurant = new Restaurant(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");
        Category category = new Category(1L,"Entrada","Papitas chips");

        Plate plate = new Plate(10L,"papitas chip",1000,"crocantes papitas chip 100gr","urlimage",category,true, restaurant);


        when(restaurantRepository.existsById(restaurant.getId())).thenReturn(false);

        Long restaurantId = restaurant.getId();

        //Act & Assert
        assertThrows(RestaurantNotExistException.class, () -> plateUseCase.validateRestaurantId(restaurantId, plate));

    }

    @Test
    void testValidateRestaurantId_PlateWithSameNameExists_ShouldThrowPlateAlreadyExistsException() {
        // Arrange
        Long restaurantId = 1L;
        Plate plate = new Plate();
        plate.setName("New Plate");
        CategoryEntity categoryEntity = new CategoryEntity();
        RestaurantEntity restaurantEntity = new RestaurantEntity();

        List<PlateEntity> existingPlates = new ArrayList<>();
        existingPlates.add(new PlateEntity(10L,"papitas chip",1000,"crocantes papitas chip 100gr","urlimage",categoryEntity, restaurantEntity, true));

        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(plateRepository.findAllByRestaurantEntityId(restaurantId)).thenReturn(existingPlates);


        // Act
        plateUseCase.validateRestaurantId(restaurantId, plate);
    }

    @Test
    void validateCategoryId() {

        Category category = new Category(1L,"Entrada","Papitas chips");

        // Act

        when(categoryRepository.existsById(1L)).thenReturn(true);

        // Assert
        assertDoesNotThrow(() -> plateUseCase.validateCategoryId(category.getId()));
    }

    @Test
    void testValidateCategorytId_NonExistinCgategoryId_ShouldThrowCategoryNotExistException() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        //Act && Assert
        assertThrows(CategoryNotExistException.class, () -> plateUseCase.validateCategoryId(categoryId));
    }

    @Test
    void testUpdatePlate_ExistingPlate_ShouldUpdatePlate() {
        // Arrange
        Long idPlate = 1L;
        UpdatePlateRequestDto updatePlateRequestDto = new UpdatePlateRequestDto();
        updatePlateRequestDto.setDescription("Alitas con salsa BBQ");
        updatePlateRequestDto.setPrice(5000);
        CategoryEntity categoryEntity = new CategoryEntity(10L,"name","description");
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "Name", "Address", "56165",
                "urlLogo.jpg", 2L, "1235156");

        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setId(idPlate);
        plateEntity.setDescription("Alistas a la broster");
        plateEntity.setPrice(500);
        plateEntity.setCategoryEntity(categoryEntity);
        plateEntity.setRestaurantEntity(restaurantEntity);

        TokenInterceptor.setIdOwner(2L);



        //Act
        when(restaurantRepository.findById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
        when(plateRepository.findById(idPlate)).thenReturn(Optional.of(plateEntity));

        // Assert
        assertDoesNotThrow(() -> plateUseCase.updatePlate(idPlate,updatePlateRequestDto));
    }

    @Test
    void testUpdatePlate_NonExistingPlate_ThrowsPlateNotExistException() {
        // Arrange
        Long idPlate = 1L;
        UpdatePlateRequestDto updatePlateRequestDto = new UpdatePlateRequestDto();
        updatePlateRequestDto.setDescription("Alitas con salsa BBQ");
        updatePlateRequestDto.setPrice(5000);

        when(plateRepository.findById(idPlate)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PlateNotExistException.class, () -> plateUseCase.updatePlate(idPlate, updatePlateRequestDto));
    }

    @Test
    void testValidateIdOwner_NoExceptionThrown() {
        // Arrange
        Long idRestaurant = 1L;
        Long idOwnerToken = 123456789L;

        TokenInterceptor.setIdOwner(idOwnerToken);


        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setIdOwner(123456789L);


        when(restaurantRepository.findById(idRestaurant)).thenReturn(Optional.of(restaurantEntity));

        // Act y Assert
        plateUseCase.validateIdOwner(idRestaurant);
    }
    @Test
    void testValidateIdOwner_ThrowsNotOwnerRestaurantException() {
        // Arrange
        Long idRestaurant = 1L;
        Long idOwnerToken = 123456789L;

        TokenInterceptor.setIdOwner(idOwnerToken);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setIdOwner(119129L);

        when(restaurantRepository.findById(idRestaurant)).thenReturn(Optional.of(restaurantEntity));

        // Act y Assert
        assertThrows(NotOwnerRestaurant.class, () -> plateUseCase.validateIdOwner(idRestaurant));
    }

}