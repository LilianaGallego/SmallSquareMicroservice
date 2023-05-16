package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IPlateHandler;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/smallsquare")
@RequiredArgsConstructor
public class PlateRestController {
    private final IPlateHandler plateHandler;

    @Operation(summary = "Add a new plate",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plate created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Plate already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/createPlate")
    public ResponseEntity<Map<String, String>> savePlate(@RequestBody PlateRequestDto plateRequestDto) {
        plateHandler.savePlate(plateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.PLATE_CREATED_MESSAGE));
    }
}
