package com.foodquart.microserviceuser.infrastructure.input.rest;

import com.foodquart.microserviceuser.application.dto.request.UserRegisterRequestDto;
import com.foodquart.microserviceuser.application.dto.response.GetUserByEmailResponseDto;
import com.foodquart.microserviceuser.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(
            summary = "Create a new restaurant owner",
            description = "Allows an ADMIN user to create a new Owner account in the system. " +
                    "The request must contain valid user data. The created user will have the OWNER role. " +
                    "Validation includes: valid email format, numeric document, phone with max 13 characters and optional '+', " +
                    "and age verification (must be over 18).",
            tags = { "Users" }
    )
    @ApiResponse(
            responseCode = "201",
            description = "Owner created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Validation failed: invalid fields like email, phone, or underage",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    @ApiResponse(
            responseCode = "403",
            description = "Forbidden: Only ADMIN users are allowed to create owners",
            content = @Content
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflict: Email or document already registered",
            content = @Content
    )
    @PostMapping("/createOwner")
    public ResponseEntity<Void> createOwner(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Owner registration details including name, document, phone, email, etc.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRegisterRequestDto.class)
                    )
            )
            @Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto
    ) {
        userHandler.createOwner(userRegisterRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get authenticated user by email",
            description = "Returns basic user data (ID and email) of the currently authenticated user. " +
                    "The user's email is extracted from the JWT token provided in the Authorization header.",
            tags = { "Users" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "User data retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GetUserByEmailResponseDto.class)
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "Unauthorized: missing or invalid JWT token",
            content = @Content
    )
    @GetMapping("/email")
    public ResponseEntity<GetUserByEmailResponseDto> getUserByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userHandler.getUserByEmail(email));
    }
}
