package com.example.demo.rest.data;

import com.example.demo.rest.ProfileController;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProfileDto {

    private UUID id;

    private String firstName;

    private String lastName;

    @NotEmpty
    private String username;

}
