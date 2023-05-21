package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
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
        return FriendsMapper.toFriendsDto(friendsList);
        //TODO inkognito nicht sichtbar
    }

    @DeleteMapping
    public void deleteFriend(@RequestHeader String authorization, @RequestBody FriendsDto friendsDto) {
        Friends actualFriends = friendsRepository.findById(friendsDto.getId()).get();
        friendsRepository.delete(actualFriends);
        //TODO throw and handle Exception
    }

}
