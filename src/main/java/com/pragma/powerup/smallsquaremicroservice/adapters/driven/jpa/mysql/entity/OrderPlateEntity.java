package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orderplate")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderPlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderEntity orderEntity;
    @ManyToOne
    @JoinColumn(name = "id_plate")
    private PlateEntity plateEntity;
    private int quantity;
}
