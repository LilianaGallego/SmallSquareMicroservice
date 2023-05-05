package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Person;

public interface IPersonServicePort {
    void savePerson(Person person);
}
