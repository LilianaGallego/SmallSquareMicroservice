package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OwnerRestController {
    private final RestTemplate restTemplate;

    @Autowired
    public OwnerRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("user/owner")
    public Object getOwner(Long id, Long idRole){
        String url ="http://localhost:8080/user/owner/getOwnerById/{id}/{idRole}";
        return restTemplate.getForObject(url, Object.class);
    }
}
