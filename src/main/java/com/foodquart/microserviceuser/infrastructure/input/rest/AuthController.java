package com.foodquart.microserviceuser.infrastructure.input.rest;

import com.foodquart.microserviceuser.application.dto.request.LoginRequestDto;
import com.foodquart.microserviceuser.application.dto.response.LoginResponseDto;
import com.foodquart.microserviceuser.application.handler.IAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandler authHandler;

    @Operation(summary = "Login to the system", description = "Allows a user (ADMIN, OWNER, EMPLOYEE, CLIENT) to authenticate using email and password. Returns a JWT token if credentials are valid.", tags = { "Authentication" })
    @ApiResponse(responseCode = "200", description = "Authentication successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request format", content = @Content)
    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Login credentials: email and password",
                    content = @Content(schema = @Schema(implementation = LoginRequestDto.class))
            )
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        return ResponseEntity.ok(authHandler.authenticate(loginRequestDto));
    }
}
