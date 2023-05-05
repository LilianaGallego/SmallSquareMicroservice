package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveUser(User user) {
        userPersistencePort.saveUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userPersistencePort.deleteUser(user);
    }

    @Override
    public List<User> getAllProviders(int page) {
        return userPersistencePort.getAllProviders(page);
    }

    @Override
    public User getProvider(Long id) {
        return userPersistencePort.getProvider(id);
    }

    @Override
    public User getEmployee(Long id) {
        return userPersistencePort.getEmployee(id);
    }

    @Override
    public User getClient(Long id) {
        return userPersistencePort.getClient(id);
    }
}
