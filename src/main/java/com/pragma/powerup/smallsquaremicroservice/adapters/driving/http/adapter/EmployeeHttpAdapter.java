package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.UserNotRoleOwnerException;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IEmployeePersistencePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class EmployeeHttpAdapter implements IEmployeePersistencePort {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${my.variables.employee}")
    String urlCreateEmployee;
    @Value("${my.variables.getEmployee}")
    String ulrGetEmployee;

    @Override
    public void createEmployee(EmployeeRequestDto user, Long idRestaurant) {
        String url = urlCreateEmployee;
        String token = TokenInterceptor.getAuthorizationToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeRequestDto> entity = new HttpEntity<>(user,headers);

        try {
           restTemplate.exchange(url, HttpMethod.POST, entity, EmployeeRequestDto.class);
        } catch (HttpClientErrorException error) {
            throw new UserNotRoleOwnerException();
        }

    }

    @Override
    public User getEmployee(String dni) {

        String url = ulrGetEmployee + dni;
        String token = TokenInterceptor.getAuthorizationToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<User> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);

        } catch (HttpClientErrorException error) {
            throw new UserNotRoleOwnerException();
        }
        return response.getBody();

    }


}
