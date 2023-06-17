package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.exception.UserNotRoleAuthorized;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IMessangerServicePersistencePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class MessangerServiceHttpAdapter  implements IMessangerServicePersistencePort {
    RestTemplate restTemplate = new RestTemplate();
    @Value("${my.variables.message}")
    String url;

    @Override
    public void sendMessageStateOrderUpdated(String message) {
        String urlMessage = url+"/message";
        sendMessageState(message,urlMessage);
    }

    @Override
    public void sendMessageState(String message, String urlMessage) {
        String token = TokenInterceptor.getAuthorizationToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(message,headers);

        try {
            restTemplate.exchange(urlMessage, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException error) {
            throw new UserNotRoleAuthorized();
        }
    }
}
