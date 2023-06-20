package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.exception.UserNotRoleAuthorized;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.ITraceabilityPersistencePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TraceabilityHttpAdapter implements ITraceabilityPersistencePort {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${my.variables.traceability}")
    String url;

    @Override
    public void saveTraceability(TraceabilityRequestDto traceabilityRequestDto) {
        String urlSave = url;
        String token = TokenInterceptor.getAuthorizationToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TraceabilityRequestDto> entity = new HttpEntity<>(traceabilityRequestDto,headers);

        try {
            restTemplate.exchange(urlSave, HttpMethod.POST, entity, TraceabilityRequestDto.class);
        } catch (HttpClientErrorException error) {
            throw new UserNotRoleAuthorized();
        }
    }
}
