package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.TraceabilityResponseDto;

public interface ITraceabilityPersistencePort {
    void saveTraceability(TraceabilityRequestDto traceabilityRequestDto);
    TraceabilityResponseDto getRecords(Long idClient);
}
