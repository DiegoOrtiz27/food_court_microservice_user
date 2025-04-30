package com.foodquart.microserviceuser.infrastructure.input.rest;

import com.foodquart.microserviceuser.application.dto.request.OwnerRequestDto;
import com.foodquart.microserviceuser.application.dto.response.IsOwnerResponseDto;
import com.foodquart.microserviceuser.application.handler.IOwnerHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    private final IOwnerHandler ownerHandler;

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

    @Operation(
            summary = "Check if user is a restaurant owner",
            description = "Returns true if the user with the given ID is registered as a restaurant owner."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Owner status retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IsOwnerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID provided",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{userId}/is-owner")
    public ResponseEntity<IsOwnerResponseDto> isUserOwner(@PathVariable Long userId) {
        boolean isOwner = ownerHandler.isUserOwner(userId);
        return ResponseEntity.ok(new IsOwnerResponseDto(isOwner));
    }
}
