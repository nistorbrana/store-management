package com.learning.storemanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StoreUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
