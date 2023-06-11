package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idClient;
    private LocalDate date;
    private String stateEnum;
    private Long idChef;
    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private RestaurantEntity restaurantEntity;

}
