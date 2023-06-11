package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderResponseDto {
    private Long idClient;
    private LocalDate date;
    private Long idChef;
    private Long idRestaurant;
    private List<OrderPlateResponseDto> orderPlatesDto;

}
