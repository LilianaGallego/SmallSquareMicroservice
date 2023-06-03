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
public class EmployeeRequestDto {

    private String name;
    private String surname;
    private String mail;
    private String phone;
    private LocalDate birthdate;
    private String dniNumber;
    private String password;
    private Long idRole;
}
