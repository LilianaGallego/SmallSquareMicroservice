package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantResponseDto {
    private String name;
    private String address;
    private String phone;
    private String urlLogo;
    private Long idOwner;
    private String dniNumber;
}
