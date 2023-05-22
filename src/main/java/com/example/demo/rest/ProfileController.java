package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.model.Profile;
import com.example.demo.repository.CustomProfileRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.rest.data.OwnProfileDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomProfileRepository customProfileRepository;


    @GetMapping("/login")
    public OwnProfileDto login(@RequestHeader String authorization) {
        return downloadProfile(authorization);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid OwnProfileDto ownProfileDto) {
        profileRepository.save(ProfileMapper.toProfile(ownProfileDto));
    }

    @GetMapping("/download")
    public OwnProfileDto downloadProfile(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        return ProfileMapper.toOwnProfileDto(profileRepository.findByUsername(username));
    }

    @PutMapping
    public void updateProfile(@RequestHeader String authorization, @RequestBody OwnProfileDto profile) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile actualProfile = customProfileRepository.findProfileByUsername(username);
        profileRepository.save(ProfileMapper.updateProfile(actualProfile, profile));

        //TODO handle Exception
    }

}
