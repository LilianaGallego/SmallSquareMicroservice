package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.PersonRequestDto;

public interface IPersonHandler {
    void savePerson(PersonRequestDto personRequestDto);
}
