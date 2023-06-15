package com.pragma.powerup.smallsquaremicroservice.domain.model;


import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.time.LocalDate;

public class Order {
    private Long id;
    private Long idClient;
    private LocalDate date;
    private StateEnum stateEnum;
    private Long idChef;
    private Restaurant restaurant;

    public Order() {
    }

    public Order(Long id, Long idClient, LocalDate date, StateEnum stateEnum, Long idChef, Restaurant restaurant) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.stateEnum = stateEnum;
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

    public StateEnum getStateEnum() {
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
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
