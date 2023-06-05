package com.metagain.backend.rest;

import com.metagain.backend.helper.AuthorizationStringSplitter;
import com.metagain.backend.helper.DistanceCalculator;
import com.metagain.backend.model.Friends;
import com.metagain.backend.model.Profile;
import com.metagain.backend.repository.CustomFriendsRepository;
import com.metagain.backend.repository.FriendsRepository;
import com.metagain.backend.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/updates")
public class UpdateController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomFriendsRepository customFriendsRepository;

    @Autowired
    private FriendsRepository friendsRepository;

    @PutMapping("/location")
    public void updateLocation(@RequestHeader String authorization, @RequestBody double[] location) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = profileRepository.findByUsername(username);
        profile.setCurrentLocation(location);
        profileRepository.save(profile);
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

}
