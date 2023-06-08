package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderPlateResponseDto {
    private Long idOrder;
    private Long idPlate;
    private int quantity;
}
