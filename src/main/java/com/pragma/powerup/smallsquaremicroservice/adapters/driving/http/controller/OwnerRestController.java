package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OwnerRestController {
    private final RestTemplate restTemplate;

    @Autowired
    public OwnerRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @CrossOrigin("*")
    @GetMapping("/user/owner/{id}")
    public User createRestaurant(@PathVariable Long id){

        String url ="http://localhost:8080/user/owner/getOwnerById/"+id;
        User user = restTemplate.getForObject(url, User.class);

        return user;
    }
}
