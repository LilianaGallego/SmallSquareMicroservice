package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class OrderResponseDto {
    private Long id;
    private Long idClient;
    private LocalDate date;
    private Long idChef;
    private Long idRestaurant;
    private List<OrderPlateResponseDto> orderPlates;

}
