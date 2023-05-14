package com.example.demo.rest;

import com.example.demo.mapper.ProfileMapper;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.rest.data.ProfileDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;


    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid ProfileDto profileDto) {
        profileRepository.save(ProfileMapper.toProfile(profileDto));
    }

}
