package com.foodquart.microserviceuser.infrastructure.input.rest;

import com.foodquart.microserviceuser.application.dto.request.CreateUserRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;
import com.foodquart.microserviceuser.application.dto.response.GetUserInfoResponseDto;
import com.foodquart.microserviceuser.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Create a new restaurant owner")
    @ApiResponse(responseCode = "201", description = "Owner created successfully")
    @ApiResponse(responseCode = "400", description = "Validation failed: invalid fields like email, phone, or underage", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "403", description = "Forbidden: Only ADMIN users are allowed to create owners", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict: Email or document already registered", content = @Content)
    @PostMapping("/createOwner")
    public ResponseEntity<CreateUserResponseDto> createOwner(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Owner registration details including name, document, phone, email, etc.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequestDto.class)
                    )
            )
            @Valid @RequestBody CreateUserRequestDto createOwnerRequestDto
    ) {
        return ResponseEntity.ok(userHandler.createOwner(createOwnerRequestDto));
    }

    @Operation(summary = "Create a new employee")
    @ApiResponse(responseCode = "201", description = "Employee created successfully")
    @ApiResponse(responseCode = "400", description = "Validation failed: invalid fields like email, phone, or underage", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "403", description = "Forbidden: Only OWNER users are allowed to create employee", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict: Email or document already registered", content = @Content)
    @PostMapping("/createEmployee")
    public ResponseEntity<CreateUserResponseDto> createEmployee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Employee registration details including name, document, phone, email, etc.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequestDto.class)
                    )
            )
            @Valid @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        return ResponseEntity.ok(userHandler.createEmployee(createUserRequestDto));
    }

    @Operation(summary = "Create a new customer")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    @ApiResponse(responseCode = "400", description = "Validation failed: invalid fields like email, phone, or underage", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "409", description = "Conflict: Email or document already registered", content = @Content)
    @PostMapping("/createCustomer")
    public ResponseEntity<CreateUserResponseDto> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Customer registration details including name, document, phone, email, etc.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequestDto.class)
                    )
            )
            @Valid @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        return ResponseEntity.ok(userHandler.createCustomer(createUserRequestDto));
    }

    @Operation(summary = "Get user information")
    @ApiResponse(responseCode = "200", description = "User information retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserInfoResponseDto> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(userHandler.getUserInfo(userId));
    }
}
