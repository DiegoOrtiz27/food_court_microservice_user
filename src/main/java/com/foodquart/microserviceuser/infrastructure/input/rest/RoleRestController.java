package com.foodquart.microserviceuser.infrastructure.input.rest;

import com.foodquart.microserviceuser.application.dto.response.HasRoleResponseDto;
import com.foodquart.microserviceuser.application.handler.IRoleHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RoleRestController {

    private final IRoleHandler roleHandler;

    @Operation(
            summary = "Check if a user has a specific role",
            description = "Returns true if the user identified by {userId} has the role {roleName}. " +
                    "Valid role names are ADMIN, OWNER, EMPLOYEE, CLIENT.",
            tags = { "Roles" }
    )
    @ApiResponse(responseCode = "200", description = "Role check successful",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Boolean.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Invalid role name or userId parameter")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{userId}/has/{roleName}")
    public ResponseEntity<HasRoleResponseDto> hasRole(
            @Parameter(
                    name = "userId",
                    description = "Identifier of the user to check",
                    required = true,
                    example = "123",
                    in = ParameterIn.PATH
            )
            @PathVariable Long userId,

            @Parameter(
                    name = "roleName",
                    description = "Name of the role to check (ADMIN, OWNER, EMPLOYEE, CLIENT)",
                    required = true,
                    example = "OWNER",
                    in = ParameterIn.PATH
            )
            @PathVariable String roleName
    ) {
        return ResponseEntity.ok(roleHandler.hasRole(userId, roleName.toUpperCase()));
    }
}
