package com.example.demo.mapper;

import com.example.demo.helper.DistanceCalculator;
import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import com.example.demo.rest.data.FriendsDto;

import java.util.ArrayList;
import java.util.List;

public class FriendsMapper {

    public static List<FriendsDto> toFriendsDto(List<Friends> friends) {
        List<FriendsDto> friendsDtos = new ArrayList<>();
        for (Friends friend : friends) {
            friendsDtos.add(toFriendsDto(friend));
        }
        return friendsDtos;
    }

    public static FriendsDto toFriendsDto(Friends friends) {
        FriendsDto friendsDto = new FriendsDto();
        Profile profile1 =  friends.getProfile1();
        Profile profile2 = friends.getProfile2();
        friendsDto.setProfile1(ProfileMapper.toProfile(profile1));
        friendsDto.setProfile2(ProfileMapper.toProfile(profile2));
        friendsDto.setRadius(friends.getRadius());
        double[] coordinates1 = profile1.getCurrentLocation();
        double[] coordinates2 = profile2.getCurrentLocation();
        try {
            double distance = DistanceCalculator.calculateDistance(coordinates1[0], coordinates1[1], coordinates2[0], coordinates2[1]);
            friendsDto.setInRadius(distance <= friends.getRadius() && !profile1.isIncognito() && !profile2.isIncognito());

        } catch (NullPointerException e) {
            friendsDto.setInRadius(false);
        }
        return friendsDto;
    }

}
