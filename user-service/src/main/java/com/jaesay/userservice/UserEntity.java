package com.jaesay.userservice;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String userId;
    private String encryptedPassword;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
