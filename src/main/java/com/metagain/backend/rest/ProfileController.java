package com.metagain.backend.rest;

import com.metagain.backend.helper.AuthorizationStringSplitter;
import com.metagain.backend.mapper.ProfileMapper;
import com.metagain.backend.model.Profile;
import com.metagain.backend.repository.*;
import com.metagain.backend.rest.data.OwnProfileDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomProfileRepository customProfileRepository;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private CustomFriendsRepository customFriendsRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private CustomMeetingRepository customMeetingRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private CustomRequestRepository customRequestRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid OwnProfileDto ownProfileDto) {
        profileRepository.save(ProfileMapper.toProfile(ownProfileDto));
    }

    @GetMapping
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

    @DeleteMapping
    public void deleteRequest(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile actualProfile = customProfileRepository.findProfileByUsername(username);
        friendsRepository.deleteAll(customFriendsRepository.findFriendsByProfile(actualProfile));
        meetingRepository.deleteAll(customMeetingRepository.getMeetingsForProfile(actualProfile));
        requestRepository.deleteAll(customRequestRepository.findRequestsForProfile(actualProfile));
        profileRepository.delete(actualProfile);
    }


}
