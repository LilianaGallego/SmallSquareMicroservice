package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;


import com.pragma.powerup.smallsquaremicroservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OwnerRestControllerTest {
    @Mock
    private RestTemplate restTemplate;

    private OwnerRestController ownerRestController;

    @BeforeEach
    public void setUp() {
        ownerRestController = new OwnerRestController(restTemplate);
    }

    @Test
    public void testCreateRestaurant() {
        // Arrange
        Long id = 10L;
        String url = "http://localhost:8080/user/owner/getOwnerById/" + id;
        User expectedUser = new User();
        expectedUser.setId(10L);
        expectedUser.setName("Liliana");
        expectedUser.setIdRole(2L);

        Mockito.when(restTemplate.getForObject(url, User.class)).thenReturn(expectedUser);

        // Act
        User result = ownerRestController.createRestaurant(id);

        // Assert
        assertEquals(expectedUser, result);
        Mockito.verify(restTemplate, times(1)).getForObject(url, User.class);
    }

}