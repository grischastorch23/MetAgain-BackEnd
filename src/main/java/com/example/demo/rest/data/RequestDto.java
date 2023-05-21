package com.example.demo.rest.data;

import com.example.demo.model.types.RequestType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestDto {

    private UUID id;

    @NotEmpty
    private String fromUsername;

    @NotEmpty
    private String toUsername;

    @NotNull
    private RequestType requestType;

    private int radius;

}
