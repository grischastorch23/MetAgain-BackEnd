package com.example.demo.rest.data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MeetingDto {

    @NotEmpty
    private UUID id;

    @NotNull
    private ProfileDto profile1;

    @NotNull
    private ProfileDto profile2;

    private double[] meetingPoint;

}
