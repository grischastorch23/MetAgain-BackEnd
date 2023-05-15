package com.example.demo.rest;

import com.example.demo.mapper.ProfileMapper;
import com.example.demo.model.Profile;
import com.example.demo.repository.CustomProfileRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.rest.data.ProfileDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

//    @Autowired
//    private CustomProfileRepository customProfileRepository;

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid ProfileDto profileDto) {
        profileRepository.save(ProfileMapper.toProfile(profileDto));
    }

    @GetMapping("/users")
    public Profile findByUsername(@RequestParam String username) {
        return profileRepository.findByUsername(username);
    }

    @GetMapping("/users/authenticated")
    public Profile findAuthenticatedUser(@RequestHeader String authorization) {
        String credentialsEncoded = authorization.substring(6);
        String credentials = new String(Base64.getDecoder().decode(credentialsEncoded));
        String username = credentials.split(":")[0];
        return profileRepository.findByUsername(username);
    }

}
