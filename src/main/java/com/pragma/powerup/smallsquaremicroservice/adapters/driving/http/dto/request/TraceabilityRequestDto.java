package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TraceabilityRequestDto {

    private Long idOrder;
    private Long idClient;
    private String emailClient;
    private LocalDate date;
    private String stateOld;
    private String stateNew;
    private String urlLogo;
    private Long idEmployee;
    private String emailEmployee;
}
