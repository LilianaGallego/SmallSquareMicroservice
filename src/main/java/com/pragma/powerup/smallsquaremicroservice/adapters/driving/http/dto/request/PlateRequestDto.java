package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlateRequestDto {
    private String name;
    @NotNull(message = "You must enter a price")
    private int price;
    @NotEmpty(message = "Description may not be empty")
    private String description;
    @NotEmpty(message = "UrlImage may not be empty")
    private String urlImage;
    @Min(value = 1, message = "Category id must not be less than 1")
    @Max(value = 4, message = "Category id must not be greater than 4")
    private Long idCategory;
    @Min(value = 1, message = "Restaurant id must not be less than 1")
    @Max(value = 4, message = "Restaurant id must not be greater than 4")
    private Long idRestaurant;
}
