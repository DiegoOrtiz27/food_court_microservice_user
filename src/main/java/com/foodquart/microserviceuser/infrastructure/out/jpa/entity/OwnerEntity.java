package com.foodquart.microserviceuser.infrastructure.out.jpa.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerEntity {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;
}

