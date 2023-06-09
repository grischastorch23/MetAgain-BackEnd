package com.metagain.backend.rest.data;

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
