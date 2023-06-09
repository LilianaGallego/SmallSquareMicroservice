package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class OrderResponseDto {
    private Long idClient;
    private LocalDate date;
    private StateEnum state;
    private Long idChef;
    private Long idRestaurant;
}
