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

@RestController
public class RegistrationController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomProfileRepository customProfileRepository;

    @PostMapping("/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid ProfileDto profileDto) {
        profileRepository.save(ProfileMapper.toProfile(profileDto));
    }

    @GetMapping("/users")
    public ProfileDto findByUsername(@RequestParam String username) {
        return ProfileMapper.toProfile(customProfileRepository.findProfileByUsername(username));
    }



}
