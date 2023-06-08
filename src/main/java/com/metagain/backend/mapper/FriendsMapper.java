package com.metagain.backend.mapper;

import com.metagain.backend.model.Friends;
import com.metagain.backend.model.Profile;
import com.metagain.backend.rest.data.FriendsDto;

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

    public static Friends toFriends(Profile profile1, Profile profile2) {
        Friends friends = new Friends();
        friends.setProfile1(profile1);
        friends.setProfile2(profile2);
        return friends;
    }

    public static Profile getOtherFriend(Friends friends, Profile profile) {
        if (friends.getProfile1().equals(profile)) {
            return friends.getProfile2();
        } else {
            return friends.getProfile1();
        }
    }

}
