package com.example.demo.mapper;

import com.example.demo.model.Profile;
import com.example.demo.rest.data.OwnProfileDto;
import com.example.demo.rest.data.ProfileDto;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public class ProfileMapper {
    
    public static Profile toProfile(OwnProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setEmail(profileDto.getEmail());
        profile.setUsername(profileDto.getUsername());
        profile.setPasswordHash(createDelegatingPasswordEncoder().encode(profileDto.getPassword()));
        return profile;
    }

    public static OwnProfileDto toOwnProfileDto(Profile profile) {
        OwnProfileDto profileDto = new OwnProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setEmail(profile.getEmail());
        profileDto.setUsername(profile.getUsername());
        profileDto.setIncognito(profile.isIncognito());
        return profileDto;
    }

    public static Profile updateProfile(Profile oldProfile, OwnProfileDto newProfile) {
        oldProfile.setUsername(newProfile.getUsername());
        oldProfile.setEmail(newProfile.getEmail());
        oldProfile.setIncognito(newProfile.isIncognito());
        oldProfile.setPasswordHash(createDelegatingPasswordEncoder().encode(newProfile.getPassword()));
        if (oldProfile.isIncognito()) {
            oldProfile.setCurrentLocation(null);
        }
        return oldProfile;
    }

    public static ProfileDto toProfileDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setUsername(profile.getUsername());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        return profileDto;
    }

}
