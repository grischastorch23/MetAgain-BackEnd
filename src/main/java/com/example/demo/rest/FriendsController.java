package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
//import com.example.demo.mapper.FriendsMapper;
import com.example.demo.helper.DistanceCalculator;
import com.example.demo.mapper.FriendsMapper;
import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import com.example.demo.repository.CustomFriendsRepository;
import com.example.demo.repository.CustomProfileRepository;
import com.example.demo.repository.FriendsRepository;
import com.example.demo.rest.data.FriendsDto;
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



    @GetMapping
    public List<FriendsDto> getAllFriends(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile =  customProfileRepository.findProfileByUsername(username);
        List<Friends> friendsList = customFriendsRepository.findFriendsByProfile(profile);
        return FriendsMapper.toFriendsDto(friendsList, profile);
        //TODO inkognito nicht sichtbar
    }

    @PutMapping
    public void updateFriends(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        if (!profile.isIncognito()) {
            List<Friends> friendsList = customFriendsRepository.findFriendsByProfile(profile);
            for (Friends friend : friendsList) {
                double[] coordinates1 = friend.getProfile2().getCurrentLocation();
                double[] coordinates2 = friend.getProfile1().getCurrentLocation();
                try {
                    double distance = DistanceCalculator.calculateDistance(coordinates1[0], coordinates1[1], coordinates2[0], coordinates2[1]);
                    friend.setInRadius(distance <= friend.getRadius() && friend.getRadius() != 0);
                } catch (NullPointerException e) {
                    friend.setInRadius(false);
                }

            }
            friendsRepository.saveAll(friendsList);
        }
    }

    //TODO add Friends instead of accepting request

    @DeleteMapping
    public void deleteFriend(@RequestHeader String authorization, @RequestBody UUID id) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        Friends actualFriends = customFriendsRepository.findByIdAndProfile(id, profile);
        friendsRepository.delete(actualFriends);
        //TODO throw and handle Exception
    }

}
