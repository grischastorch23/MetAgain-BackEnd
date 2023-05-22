package com.example.demo.mapper;

import com.example.demo.helper.DistanceCalculator;
import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import com.example.demo.rest.data.FriendsDto;

import java.util.ArrayList;
import java.util.List;

public class FriendsMapper {

    public static List<FriendsDto> toFriendsDto(List<Friends> friends, Profile profile) {
        List<FriendsDto> friendsDtos = new ArrayList<>();
        for (Friends friend : friends) {
            friendsDtos.add(toFriendsDto(friend, profile));
        }
        return friendsDtos;
    }

    public static FriendsDto toFriendsDto(Friends friends, Profile profile) {
        FriendsDto friendsDto = new FriendsDto();
        Profile friendsProfile = (friends.getProfile1().equals(profile)) ? friends.getProfile2() : friends.getProfile1();
        friendsDto.setId(friends.getId());
        friendsDto.setFriendsProfile(ProfileMapper.toProfileDto(friendsProfile));
        friendsDto.setRadius(friends.getRadius());
        friendsDto.setInRadius(friends.isInRadius());
        return friendsDto;
    }

}
