package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantEntityTest {
    @Test
    void testGetters(){
        //Arrange
        RestaurantEntity restaurantEntity = new RestaurantEntity(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");
        // Act & Assert
        assertEquals("Las delicias de la 5ta", restaurantEntity.getName());
        assertEquals("clle 19 N°19-22", restaurantEntity.getAddress());
        assertEquals("18181818", restaurantEntity.getPhone());
        assertEquals("https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                restaurantEntity.getUrlLogo());
        assertEquals(10L, restaurantEntity.getIdOwner());
        assertEquals("199191919", restaurantEntity.getDniNumber());

    }

    @Test
    void testSetters(){
        //Arrange
        RestaurantEntity entity = new RestaurantEntity();

        // Act
        entity.setId(1L);
        entity.setName("Las delicias de la 5ta");
        entity.setAddress("clle 19 N°19-22");
        entity.setPhone("18181818");
        entity.setUrlLogo("https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525");
        entity.setIdOwner(1L);
        entity.setDniNumber("199191919");

        // Assert
        assertEquals("Las delicias de la 5ta", entity.getName());
        assertEquals("clle 19 N°19-22", entity.getAddress());
        assertEquals("18181818", entity.getPhone());
        assertEquals("https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                entity.getUrlLogo());
        assertEquals(1L, entity.getIdOwner());
        assertEquals("199191919", entity.getDniNumber());
    }



}