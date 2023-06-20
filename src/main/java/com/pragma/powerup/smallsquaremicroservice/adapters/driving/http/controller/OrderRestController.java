package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.TraceabilityResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.smallsquaremicroservice.configuration.Constants;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;
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
public class OrderRestController {
    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/order")
    public ResponseEntity<Map<String, String>> saveOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderHandler.saveOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CREATED_MESSAGE));
    }

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get all orders by state",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Orders by state",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Plate not found ",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @GetMapping("/orders/byState")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByStateEnum(@RequestParam StateEnum stateEnum,@RequestParam int page, @RequestParam int size) {

        return ResponseEntity.ok(orderHandler.getAllOrdersByStateEnum(stateEnum,page, size));
    }



    @Operation(summary = "Updated state order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Updated order",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order no exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/order/state/{idOrder}")
    public ResponseEntity<List<OrderResponseDto>> updateStateOrder( @PathVariable Long idOrder, @RequestParam StateEnum stateEnum,@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(orderHandler.updateStatusOrder(idOrder, stateEnum, page, size));
    }

    @Operation(summary = "Updated state order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Updated order",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order no exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/order/ready/{idOrder}")
    public ResponseEntity<Map<String, String>> updateOrderReady( @PathVariable Long idOrder) {
        orderHandler.updateOrderReady(idOrder);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_STATE_READY_MESSAGE));
    }

    @Operation(summary = "Updated state order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Updated order",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order no exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/order/delivered/{idOrder}/{codeClient}")
    public ResponseEntity<Map<String, String>> updateOrderDelivered( @PathVariable Long idOrder, @PathVariable int codeClient) {
        orderHandler.updateOrderDelivered(idOrder, codeClient);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_STATE_READY_MESSAGE));
    }

    @Operation(summary = "Cancel Order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cancel oder",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order no exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/order/cancel/{idOrder}")
    public ResponseEntity<Map<String, String>> cancelOrder( @PathVariable Long idOrder) {
        orderHandler.cancelOrder(idOrder);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_STATE_CANCEL_MESSAGE));
    }

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get all records by client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Records by client",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Record not found ",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @GetMapping("/records/byClient")
    public ResponseEntity<List<TraceabilityResponseDto>> getAllRecordsOrdersByClient() {

        return ResponseEntity.ok(orderHandler.getAllRecordsOrdersByClient());
    }
}
