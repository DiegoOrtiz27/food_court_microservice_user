package com.foodquart.microserviceuser.infrastructure.input.rest;

import com.foodquart.microserviceuser.application.dto.request.OwnerRequestDto;
import com.foodquart.microserviceuser.application.handler.impl.OwnerHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerRestController {

    private final OwnerHandler ownerHandler;

    @Operation(summary = "Add a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Owner already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> createOwner(@Valid @RequestBody OwnerRequestDto ownerRequestDto) {
        ownerHandler.saveOwner(ownerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
