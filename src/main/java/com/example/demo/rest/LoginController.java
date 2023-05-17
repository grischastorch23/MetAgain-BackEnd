package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.rest.data.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class LoginController {

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/login")
    public ProfileDto login(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        return ProfileMapper.toProfile(profileRepository.findByUsername(username));
    }

}
