package com.example.demo.rest.data;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class FriendsDto {

    @NotEmpty
    private UUID id;

    @NotEmpty
    private ProfileDto profile1;

    @NotEmpty
    private ProfileDto profile2;

    private int radius;

    private boolean inRadius;

}
