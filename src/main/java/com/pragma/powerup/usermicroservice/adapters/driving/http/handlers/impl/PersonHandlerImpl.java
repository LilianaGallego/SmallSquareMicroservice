package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.PersonRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IPersonHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IPersonRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IPersonServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonHandlerImpl implements IPersonHandler {
    private final IPersonServicePort personServicePort;
    private final IPersonRequestMapper personRequestMapper;

    @Override
    public void savePerson(PersonRequestDto personRequestDto) {
        personServicePort.savePerson(personRequestMapper.toPerson(personRequestDto));
    }
}
