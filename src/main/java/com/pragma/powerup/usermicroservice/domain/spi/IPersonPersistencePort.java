package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Person;

public interface IPersonPersistencePort {
    void savePerson(Person person);
}
