package com.pragma.powerup.smallsquaremicroservice.domain.spi;

public interface IMessangerServicePersistencePort {
    void sendMessageStateOrderUpdated(String message);
    void sendMessageStateOrderDelivered(String message);
    void sendMessageState(String message, String urlMessage);
}
