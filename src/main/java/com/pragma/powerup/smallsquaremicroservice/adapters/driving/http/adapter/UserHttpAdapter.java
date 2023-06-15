package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IUserHttpPersistencePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserHttpAdapter implements IUserHttpPersistencePort {
    RestTemplate restTemplate = new RestTemplate();
    @Value("${my.variables.url}")
    String url;

    @Value("${my.variables.getClient}")
    String urlClient;
    @Override
    public User getOwner(Long id) {
        String urlId = url + id;
        return getuser(urlId);
    }

    @Override
    public User getClient(Long id) {
        String urlId = urlClient + id;
        return getuser(urlId);
    }

    @Override
    public User getuser(String urlId) {
        String token = TokenInterceptor.getAuthorizationToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<User> response;

        try {
            response = restTemplate.exchange(urlId, HttpMethod.GET, entity, User.class);
        } catch (HttpClientErrorException error) {
            throw new UserNotFoundException();
        }
        return response.getBody();
    }
}
