package com.metagain.backend.rest;

import com.metagain.backend.helper.AuthorizationStringSplitter;
//import com.example.demo.mapper.FriendsMapper;
import com.metagain.backend.helper.DistanceCalculator;
import com.metagain.backend.mapper.FriendsMapper;
import com.metagain.backend.model.Friends;
import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import com.metagain.backend.model.types.RequestType;
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

        for (Friends friend : friendsList) {
            double[] coordinates1 = friend.getProfile2().getCurrentLocation();
            double[] coordinates2 = friend.getProfile1().getCurrentLocation();
            try {
                double distance = DistanceCalculator.calculateDistance(coordinates1[0], coordinates1[1], coordinates2[0], coordinates2[1]);
                if (!FriendsMapper.getOtherFriend(friend, profile).isIncognito()) {
                    friend.setInRadius(distance <= friend.getRadius() && friend.getRadius() != 0);
                }
            } catch (NullPointerException e) {
                friend.setInRadius(false);
            }

        }
        return FriendsMapper.toFriendsDto(friendsList, profile);
    }


    @DeleteMapping(path = "/{id}")
    public void deleteFriend(@RequestHeader String authorization, @PathVariable UUID id) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        Friends actualFriends = customFriendsRepository.findByIdAndProfile(id, profile);
        friendsRepository.delete(actualFriends);
        //TODO throw and handle Exception
    }

}
