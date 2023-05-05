package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.PersonResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IPersonResponseMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IPersonResponseMapper personResponseMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        userServicePort.saveUser(userRequestMapper.toUser(userRequestDto));
    }

    @Override
    public void deleteUser(UserRequestDto userRequestDto) {
        userServicePort.deleteUser(userRequestMapper.toUser(userRequestDto));
    }

    @Override
    public List<PersonResponseDto> getProvider(Integer page) {
        return personResponseMapper.userListToPersonResponseList(userServicePort.getAllProviders(page));
    }

    @Override
    public PersonResponseDto getProvider(Long id) {
        return personResponseMapper.userToPersonResponse(userServicePort.getProvider(id));
    }

    @Override
    public PersonResponseDto getEmployee(Long id) {
        return personResponseMapper.userToPersonResponse(userServicePort.getEmployee(id));
    }

    @Override
    public PersonResponseDto getClient(Long id) {
        return personResponseMapper.userToPersonResponse(userServicePort.getClient(id));
    }
}
