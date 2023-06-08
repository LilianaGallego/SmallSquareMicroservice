package com.pragma.powerup.smallsquaremicroservice.domain.model;

import java.time.LocalDate;

public class Order {
    private Long id;
    private Long idClient;
    private LocalDate date;
    private String state;
    private Long idChef;
    private Restaurant restaurant;

    public Order() {
    }

    public Order(Long id, Long idClient, LocalDate date, String state, Long idChef, Restaurant restaurant) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.state = state;
        this.idChef = idChef;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getIdChef() {
        return idChef;
    }

    public void setIdChef(Long idChef) {
        this.idChef = idChef;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
