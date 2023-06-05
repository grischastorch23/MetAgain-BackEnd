package com.metagain.backend.rest;

import com.metagain.backend.helper.AuthorizationStringSplitter;
//import com.example.demo.mapper.RestrictionMapper;
import com.metagain.backend.mapper.RestrictionMapper;
import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Restriction;
import com.metagain.backend.repository.CustomProfileRepository;
import com.metagain.backend.repository.CustomRestrictionRepository;
import com.metagain.backend.repository.ProfileRepository;
import com.metagain.backend.repository.RestrictionRepository;
import com.metagain.backend.rest.data.RestrictionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restrictions")
public class RestrictionController {

    @Autowired
    private RestrictionRepository restrictionRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomRestrictionRepository customRestrictionRepository;

    @Autowired
    private CustomProfileRepository customProfileRepository;




    @PostMapping
    public void addRestriction(@RequestHeader String authorization, @RequestBody @Valid RestrictionDto restrictionDto) {
        String fromUsername = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        String toUsername = restrictionDto.getToUsername();
        Profile fromProfile = customProfileRepository.findProfileByUsername(fromUsername);
        Profile toProfile = customProfileRepository.findProfileByUsername(toUsername);
        Restriction restriction = new Restriction();
        restriction.setRestrictionType(restrictionDto.getRestrictionType());
        restriction.setToProfile(toProfile);
        restriction.setFromProfile(fromProfile);
        restrictionRepository.save(restriction);
    }

    @GetMapping
    public RestrictionDto getRestriction(@RequestHeader String authorization, @RequestBody String username) {
        Profile fromProfile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        Profile toProfile = profileRepository.findByUsername(username);
        return RestrictionMapper.toRestrictionDto(customRestrictionRepository.findByBothProfiles(fromProfile, toProfile));
        //TODO handle Exceptions
    }

    @DeleteMapping
    public void deleteRestriction(@RequestHeader String authorization, @RequestBody UUID id) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = profileRepository.findByUsername(username);
        Restriction actualRestriction = customRestrictionRepository.findByIdAndProfile(id, profile);
        restrictionRepository.delete(actualRestriction);
    }

}
