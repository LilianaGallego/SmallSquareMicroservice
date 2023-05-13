package com.pragma.powerup.smallsquaremicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plate {

    private Long id;
    private String name;
    private int price;
    private String description;
    private String urlImage;
    private Category category;
    private Boolean active;
    private Restaurant restaurant;
}
