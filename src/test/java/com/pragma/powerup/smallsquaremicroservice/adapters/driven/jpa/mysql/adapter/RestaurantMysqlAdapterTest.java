package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class RestaurantMysqlAdapterTest {

    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;


    @InjectMocks
    private RestaurantMysqlAdapter restaurantMysqlAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given a user when saveRestaurant then save the user in the repository")
    void saveRestaurantTest() {
        // Arrange


        Restaurant restaurant = new Restaurant(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");

        RestaurantEntity restaurantEntity = new RestaurantEntity(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");


        Mockito.when(restaurantRepository.findByDniNumber(restaurant.getDniNumber())).thenReturn(Optional.empty());
        Mockito.when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(restaurantEntity);


        // Act
        restaurantMysqlAdapter.saveRestaurant(restaurant);

        // Assert
        verify(restaurantRepository, times(1)).save(restaurantEntity);
    }

    @Test
    @DisplayName("Given an existing restaurant dni number when saveRestaurant then throw RestaurantAlreadyExistsException")
    void saveRestaurantWithExistingDniNumberTest() {
        // Arrange


        Restaurant restaurant = new Restaurant(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");

        RestaurantEntity restaurantEntity = new RestaurantEntity(10L,"Las delicias de la 5ta","clle 19 N°19-22",
                "18181818",
                "https://jimdo-storage.freetls.fastly.net/image/9939456/d2e94e18-d535-4d67-87ef-e96f4d1b591f.png?quality=80,90&auto=webp&disable=upscale&width=455.23809523809524&height=239&crop=1:0.525",
                10L, "199191919");

        when(restaurantRepository.findByDniNumber(restaurant.getDniNumber())).thenReturn(Optional.of(restaurantEntity));

        // Act & Assert
       assertThrows(RestaurantAlreadyExistsException.class, () -> restaurantMysqlAdapter.saveRestaurant(restaurant));
    }


}