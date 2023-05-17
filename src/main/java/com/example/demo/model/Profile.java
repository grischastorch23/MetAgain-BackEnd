package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Profile {

    @Id
    private UUID id = UUID.randomUUID();
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    @Column(unique=true)
    private String email;
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    private String passwordHash;
}
