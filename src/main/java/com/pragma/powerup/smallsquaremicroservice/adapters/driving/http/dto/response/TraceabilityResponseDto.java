package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class TraceabilityResponseDto {
    private Long idOrder;
    private Long idClient;
    private String emailClient;
    private LocalDate date;
    private String stateOld;
    private String stateNew;
    private Long idEmployee;
    private String emailEmployee;
}
