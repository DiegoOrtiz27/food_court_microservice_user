package com.foodquart.microserviceuser.domain.model;

import com.foodquart.microserviceuser.domain.util.Role;
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
    private Role role;
}