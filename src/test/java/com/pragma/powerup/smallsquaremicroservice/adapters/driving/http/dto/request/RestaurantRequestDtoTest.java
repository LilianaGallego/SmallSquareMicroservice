package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantRequestDtoTest {
    @Test
    void testValidation_ValidDto() {
        // Arrange
        RestaurantRequestDto dto = new RestaurantRequestDto("La ricuras de la 5ta","calle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");


        // Act and Assert
        assertEquals("Las ricuras de la 5ta", dto.getName());
        assertEquals("calle 19 N°19-22", dto.getAddress());
        assertEquals("18181818", dto.getPhone());
        assertEquals("https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                dto.getUrlLogo());
        assertEquals(10L, dto.getIdOwner());
        assertEquals("199191919", dto.getDniNumber());


    }

    @Test
    void testValidation_InvalidDto() {
        // Arrange
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto("","",
                "181818183993939393", "", null, "123");


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Act
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantRequestDto);

        // Assert
        assertEquals(7, violations.size());
        for (ConstraintViolation<RestaurantRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}