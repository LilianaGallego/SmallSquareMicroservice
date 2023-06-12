package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class OrderResponseDto {
    private Long id;
    private Long idClient;
    private LocalDate date;
    private Long idChef;
    private Long idRestaurant;
    private List<OrderPlate> orderPlates;

}
