package com.pragma.powerup.smallsquaremicroservice.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {


    private String name;
    private String surname;
    private String mail;
    private String phone;
    private Date birthdate;
    private String dniNumber;
    private String password;
    private Long idRole;
    public User(){}


    public User(String name, String surname, String mail, String phone, Date birthdate, String dniNumber,
                String password, Long idRole) {

        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.phone = phone;
        this.birthdate = birthdate;
        this.dniNumber = dniNumber;
        this.password = password;
        this.idRole = idRole;
    }


}
