package com.foodquart.microserviceuser.domain.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentId;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private RoleModel role;
}