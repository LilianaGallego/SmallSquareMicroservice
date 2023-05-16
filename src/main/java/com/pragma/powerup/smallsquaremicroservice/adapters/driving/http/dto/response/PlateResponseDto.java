package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlateResponseDto {

    private String name;
    private int price;
    private String description;
    private String urlImage;
    private Long idCategory;
    private Long idRestaurant;

}
