package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private LocalDate birthdate;
    private String dniNumber;
    private String password;
    private Long idRole;
}
