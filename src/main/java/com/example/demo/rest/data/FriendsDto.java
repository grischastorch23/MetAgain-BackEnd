package com.example.demo.rest.data;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class FriendsDto {

    @NotEmpty
    private UUID id;

    @NotEmpty
    private ProfileDto friendsProfile;

    private int radius;

    private boolean inRadius;

}
