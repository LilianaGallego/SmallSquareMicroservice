package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request;

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
    private int price;

    private String description;

    private String urlImage;

    private Long idCategory;

    private Long idRestaurant;
}
