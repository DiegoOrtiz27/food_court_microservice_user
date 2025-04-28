package com.foodquart.microserviceuser.domain.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OwnerModel {
    private Long id;
    private UserModel user;
}
