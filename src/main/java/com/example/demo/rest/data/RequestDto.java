package com.example.demo.rest.data;

import com.example.demo.model.RequestType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class RequestDto {

    @Id
    private UUID id;

    @NotEmpty
    private ProfileDto fromProfile;

    @NotEmpty
    private ProfileDto toProfile;

    private RequestType requestType;

    public UUID getId() {
        return id;
    }

    public ProfileDto getFromProfile() {
        return fromProfile;
    }

    public ProfileDto getToProfile() {
        return toProfile;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFromProfile(ProfileDto fromProfile) {
        this.fromProfile = fromProfile;
    }

    public void setToProfile(ProfileDto toProfile) {
        this.toProfile = toProfile;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
