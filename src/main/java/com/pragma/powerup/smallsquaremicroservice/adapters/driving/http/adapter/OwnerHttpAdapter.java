package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class OwnerHttpAdapter {
    RestTemplate restTemplate = new RestTemplate();
    TokenInterceptor tokenInterceptor = new TokenInterceptor();
    @Value("${my.variables.url}")
    String url;


    public User getOwner(Long id) {
        String urlId = url + id;
        String token = tokenInterceptor.getAuthorizationToken();
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
