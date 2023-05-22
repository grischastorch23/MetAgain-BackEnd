package com.example.demo.rest.data;

import com.example.demo.model.types.RequestType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestDto {

    private UUID id;

    @NotNull
    private ProfileDto profile;

    @NotNull
    private RequestType requestType;

    private int radius;

}
