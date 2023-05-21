package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    private ProfileRepository profileRepository;

    @PutMapping("/location")
    public void updateLocation(@RequestHeader String authorization, @RequestBody double[] location) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = profileRepository.findByUsername(username);
        profile.setCurrentLocation(location);
        profileRepository.save(profile);
    }

}
