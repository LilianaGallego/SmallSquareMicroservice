package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Person;
import com.pragma.powerup.usermicroservice.domain.spi.IPersonPersistencePort;
import com.pragma.powerup.usermicroservice.domain.api.IPersonServicePort;

public class PersonUseCase implements IPersonServicePort {
    private final IPersonPersistencePort personPersistencePort;

    public PersonUseCase(IPersonPersistencePort personPersistencePort) {
        this.personPersistencePort = personPersistencePort;
    }

    @Override
    public void savePerson(Person person) {
        personPersistencePort.savePerson(person);
    }
}
