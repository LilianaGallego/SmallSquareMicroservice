package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class OrderResponseDto {
    private Long idClient;
    private LocalDate date;
    private String state;
    private Long idChef;
    private Long idRestaurant;
}
