package com.metagain.backend.rest.data;

import com.metagain.backend.model.types.RestrictionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RestrictionDto {

    private UUID id;

    @NotEmpty
    private String fromUsername;

    @NotEmpty
    private String toUsername;

    @NotNull
    private RestrictionType restrictionType;
}
