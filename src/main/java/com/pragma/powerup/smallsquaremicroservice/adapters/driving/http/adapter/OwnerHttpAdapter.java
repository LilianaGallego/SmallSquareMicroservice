package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class OwnerHttpAdapter {
    RestTemplate restTemplate = new RestTemplate();
    @Value("${my.variables.url}")
    String url;

    public User getOwner(Long id) {
        String urlId = url + id;
        ResponseEntity<User> response;
        try {
            response = restTemplate.exchange(urlId, HttpMethod.GET, null, User.class);
        } catch (HttpClientErrorException error) {
            throw new UserNotFoundException();
        }
        return response.getBody();
    }
}
