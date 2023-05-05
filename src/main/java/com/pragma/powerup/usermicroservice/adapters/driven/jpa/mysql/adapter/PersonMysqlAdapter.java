package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.MailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IPersonRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IPersonEntityMapper;
import com.pragma.powerup.usermicroservice.domain.model.Person;
import com.pragma.powerup.usermicroservice.domain.spi.IPersonPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PersonMysqlAdapter implements IPersonPersistencePort {
    private final IPersonRepository personRepository;
    private final IPersonEntityMapper personEntityMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void savePerson(Person person) {
        if (personRepository.findByDniNumber(person.getDniNumber()).isPresent()) {
            throw new PersonAlreadyExistsException();
        }
        if (personRepository.existsByMail(person.getMail())){
            throw new MailAlreadyExistsException();
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(personEntityMapper.toEntity(person));
    }
}
