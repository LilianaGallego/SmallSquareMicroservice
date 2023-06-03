package com.pragma.powerup.smallsquaremicroservice.domain.dtouser;

public class RestaurantEmployee {
    private Long  idEmployee;
    private Long idRestaurant;

    public RestaurantEmployee() {
    }

    public RestaurantEmployee(Long idEmployee, Long idRestaurant) {
        this.idEmployee = idEmployee;
        this.idRestaurant = idRestaurant;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }


}

