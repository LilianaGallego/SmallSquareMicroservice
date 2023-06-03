package com.pragma.powerup.smallsquaremicroservice.domain.dtouser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

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
