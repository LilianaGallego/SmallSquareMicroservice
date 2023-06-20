package com.pragma.powerup.smallsquaremicroservice.domain.spi;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;

public interface ITraceabilityPersistencePort {
    void saveTraceability(TraceabilityRequestDto traceabilityRequestDto);
}
