package com.pragma.powerup.smallsquaremicroservice.domain.spi;

public interface IMessangerServicePersistencePort {
    void sendMessageOrderReady(String message);
}
