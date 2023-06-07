package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.PlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.UpdatePlateRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.PlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IPlateHandler;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/smallsquare")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class PlateRestController {
    private final IPlateHandler plateHandler;

    @Operation(summary = "Add a new plate",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plate created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Plate already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/plate/create")
    public ResponseEntity<Map<String, String>> savePlate(@RequestBody PlateRequestDto plateRequestDto) {
        plateHandler.savePlate(plateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.PLATE_CREATED_MESSAGE));
    }

    @Operation(summary = "Updated plate",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Updated plate",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Plate no exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/plate/update/{idPlate}")
    public ResponseEntity<Map<String, String>> updatePlate( @PathVariable Long idPlate, @RequestBody UpdatePlateRequestDto updatePlateRequesDto) {
        plateHandler.updatePlate(idPlate,updatePlateRequesDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.PLATE_UPDATED_MESSAGE));
    }

    @Operation(summary = "Updated status plate",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Updated plate",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Plate no exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/plate/update/status/{idPlate}")
    public ResponseEntity<Map<String, String>> updateStatusPlate( @PathVariable Long idPlate) {
        plateHandler.updateStatusPlate(idPlate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.PLATE_UPDATED_MESSAGE));
    }

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get all plates by restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plates by restaurant",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Plate not found ",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @GetMapping("/plates/byRestaurant")
    public ResponseEntity<List<PlateResponseDto>> getAllPlatesByRestaurant(@RequestParam Long idRestaurant, @RequestParam Long idCategory, @RequestParam int page, @RequestParam int size) {

        return ResponseEntity.ok(plateHandler.getAllPlatesByRestaurant(idRestaurant,idCategory,page, size));
    }
}
