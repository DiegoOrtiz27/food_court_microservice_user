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

import static com.foodquart.microserviceuser.infrastructure.documentation.APILoginDocumentationConstant.*;
import static com.foodquart.microserviceuser.infrastructure.documentation.ResponseCode.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandler authHandler;

    @Operation(summary = LOGIN_SUMMARY, description = LOGIN_DESCRIPTION, tags = { LOGIN_TAG })
    @ApiResponse(responseCode = CODE_200, description = LOGIN_SUCCESS_DESCRIPTION, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class)))
    @ApiResponse(responseCode = CODE_400, description = LOGIN_INVALID_REQUEST_DESCRIPTION, content = @Content)
    @ApiResponse(responseCode = CODE_401, description = LOGIN_INVALID_CREDENTIALS_DESCRIPTION, content = @Content)
    @ApiResponse(responseCode = CODE_404, description = LOGIN_USER_NOT_FOUND_DESCRIPTION, content = @Content)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = LOGIN_REQUEST_BODY_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = LoginRequestDto.class))
            )
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        return ResponseEntity.ok(authHandler.authenticate(loginRequestDto));
    }
}
