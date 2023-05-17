package com.example.demo.rest;


import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import com.example.demo.repository.CustomRequestRepository;
import com.example.demo.repository.FriendsRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.RequestRepository;
import com.example.demo.rest.data.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accept")
public class AcceptController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private CustomRequestRepository customRequestRepository;

    @PostMapping("/follow")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void acceptFollow(@RequestHeader String authorization, @RequestBody RequestDto request) {
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(request.getId(), profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]));
        Profile fromProfile = actualRequest.getFromProfile();
        Profile toProfile = actualRequest.getToProfile();
        Friends friends = new Friends();
        friends.setProfile1(fromProfile);
        friends.setProfile2(toProfile);
        requestRepository.delete(actualRequest);
        friendsRepository.save(friends);
    }

}
