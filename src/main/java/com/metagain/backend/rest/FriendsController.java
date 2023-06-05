package com.metagain.backend.rest;

import com.metagain.backend.helper.AuthorizationStringSplitter;
//import com.example.demo.mapper.FriendsMapper;
import com.metagain.backend.mapper.FriendsMapper;
import com.metagain.backend.model.Friends;
import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import com.metagain.backend.model.types.RequestType;
import com.example.demo.repository.*;
import com.metagain.backend.repository.*;
import com.metagain.backend.rest.data.FriendsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private CustomFriendsRepository customFriendsRepository;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private CustomProfileRepository customProfileRepository;

    @Autowired
    private CustomRequestRepository customRequestRepository;

    @Autowired
    private RequestRepository requestRepository;



    @GetMapping
    public List<FriendsDto> getAllFriends(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile =  customProfileRepository.findProfileByUsername(username);
        List<Friends> friendsList = customFriendsRepository.findFriendsByProfile(profile);
        return FriendsMapper.toFriendsDto(friendsList, profile);
    }

    @PutMapping
    public void updateFriends(@RequestHeader String authorization, @RequestBody UUID requestId) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        Request request = customRequestRepository.findRequestByIdAndProfile(requestId, profile);
        Profile fromProfile = request.getFromProfile();
        if (request.getRequestType().equals(RequestType.RADIUS)) {
            Friends friends = customFriendsRepository.findFriendsByBoth(profile, fromProfile);
            friends.setRadius(request.getRadius());
            friendsRepository.save(friends);
        } else {
            //TODO
        }
    }

    @PostMapping
    public void addFriend(@RequestHeader String authorization, @RequestBody UUID requestId) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(requestId, profile);
        if (actualRequest.getRequestType().equals(RequestType.FOLLOW)) {
            Friends friends = FriendsMapper.toFriends(profile, actualRequest.getFromProfile());
            friendsRepository.save(friends);
            requestRepository.delete(actualRequest);
        } else {
            //TODO
        }
    }

    @DeleteMapping
    public void deleteFriend(@RequestHeader String authorization, @RequestBody UUID id) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        Friends actualFriends = customFriendsRepository.findByIdAndProfile(id, profile);
        friendsRepository.delete(actualFriends);
        //TODO throw and handle Exception
    }

}
