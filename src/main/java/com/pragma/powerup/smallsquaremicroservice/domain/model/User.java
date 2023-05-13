package com.pragma.powerup.smallsquaremicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private String name;
    private String surname;
    private String mail;
    private String phone;
    private Date birthdate;
    private String dniNumber;
    private String password;
    private Long idRole;





}
