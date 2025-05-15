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

import static com.foodquart.microserviceuser.infrastructure.documentation.APIUserDocumentationConstant.*;
import static com.foodquart.microserviceuser.infrastructure.documentation.ResponseCode.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserRestController {

    private final IUserHandler userHandler;

    @PostMapping("/createOwner")
    @Operation(summary = CREATE_OWNER_SUMMARY)
    @ApiResponse(responseCode = CODE_201, description = CREATE_OWNER_SUCCESS_DESCRIPTION)
    @ApiResponse(responseCode = CODE_400, description = CREATE_OWNER_INVALID_FIELDS_DESCRIPTION, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = CODE_403, description = CREATE_OWNER_FORBIDDEN_DESCRIPTION, content = @Content)
    @ApiResponse(responseCode = CODE_409, description = CREATE_OWNER_CONFLICT_DESCRIPTION, content = @Content)
    public ResponseEntity<CreateUserResponseDto> createOwner(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = CREATE_OWNER_REQUEST_BODY_DESCRIPTION,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequestDto.class)
                    )
            )
            @Valid @RequestBody CreateUserRequestDto createOwnerRequestDto
    ) {
        return ResponseEntity.ok(userHandler.createOwner(createOwnerRequestDto));
    }

    @PostMapping("/createEmployee")
    @Operation(summary = CREATE_EMPLOYEE_SUMMARY)
    @ApiResponse(responseCode = CODE_201, description = CREATE_EMPLOYEE_SUCCESS_DESCRIPTION)
    @ApiResponse(responseCode = CODE_400, description = CREATE_EMPLOYEE_INVALID_FIELDS_DESCRIPTION, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = CODE_403, description = CREATE_EMPLOYEE_FORBIDDEN_DESCRIPTION, content = @Content)
    @ApiResponse(responseCode = CODE_409, description = CREATE_EMPLOYEE_CONFLICT_DESCRIPTION, content = @Content)
    public ResponseEntity<CreateUserResponseDto> createEmployee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = CREATE_EMPLOYEE_REQUEST_BODY_DESCRIPTION,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequestDto.class)
                    )
            )
            @Valid @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        return ResponseEntity.ok(userHandler.createEmployee(createUserRequestDto));
    }

    @PostMapping("/createCustomer")
    @Operation(summary = CREATE_CUSTOMER_SUMMARY)
    @ApiResponse(responseCode = CODE_201, description = CREATE_CUSTOMER_SUCCESS_DESCRIPTION)
    @ApiResponse(responseCode = CODE_400, description = CREATE_CUSTOMER_INVALID_FIELDS_DESCRIPTION, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = CODE_409, description = CREATE_CUSTOMER_CONFLICT_DESCRIPTION)
    public ResponseEntity<CreateUserResponseDto> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = CREATE_CUSTOMER_REQUEST_BODY_DESCRIPTION,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequestDto.class)
                    )
            )
            @Valid @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        return ResponseEntity.ok(userHandler.createCustomer(createUserRequestDto));
    }

    @GetMapping("/{userId}")
    @Operation(summary = GET_USER_INFO_SUMMARY)
    @ApiResponse(responseCode = CODE_200, description = GET_USER_INFO_SUCCESS_DESCRIPTION)
    @ApiResponse(responseCode = CODE_404, description = GET_USER_INFO_NOT_FOUND_DESCRIPTION)
    public ResponseEntity<GetUserInfoResponseDto> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(userHandler.getUserInfo(userId));
    }
}
