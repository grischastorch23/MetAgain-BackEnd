package com.example.demo.mapper;

import com.example.demo.model.Profile;
import com.example.demo.rest.data.ProfileDto;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public class ProfileMapper {
    
    public static Profile toProfile(ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setEmail(profileDto.getEmail());
        profile.setUsername(profileDto.getUsername());
        profile.setPasswordHash(createDelegatingPasswordEncoder().encode(profileDto.getPassword()));
        return profile;
    }
}
