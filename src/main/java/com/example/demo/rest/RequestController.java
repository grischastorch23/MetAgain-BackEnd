package com.example.demo.rest;

import com.example.demo.exception.*;
import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.mapper.RequestMapper;
import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import com.example.demo.model.types.RequestType;
import com.example.demo.repository.*;
import com.example.demo.rest.data.RequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private CustomFriendsRepository customFriendsRepository;

    @Autowired
    private CustomRequestRepository customRequestRepository;



    @PostMapping("/send")
    public void sendRequest(@RequestHeader String authorization, @RequestBody @Valid RequestDto requestDto) throws NoFriendsException, OwnProfileException, AlreadyFriendsException, NotInRadiusException, KindalikeException {
        String fromUsername = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile author = profileRepository.findByUsername(fromUsername);
        Profile recipient = profileRepository.findByUsername(requestDto.getProfile().getUsername());
        RequestType requestType = requestDto.getRequestType();
        if (author.equals(recipient)) {
            throw new OwnProfileException();
        } else if (requestType.equals(RequestType.FOLLOW) && customFriendsRepository.exists(author, recipient)) {
            throw new AlreadyFriendsException();
        } else if (!requestType.equals(RequestType.FOLLOW) && !customFriendsRepository.exists(author, recipient)) {
            throw new NoFriendsException();
        } else if (requestType.equals(RequestType.MEET) && !customFriendsRepository.findFriendsByBoth(author, recipient).isInRadius()) {
            throw new NotInRadiusException();
        }

        Request request = RequestMapper.toRequest(requestDto, author, recipient);

        if (customRequestRepository.existsKindalike(request)) {
            throw new KindalikeException();
        }
        requestRepository.save(request);
    }

    @GetMapping
    public List<RequestDto> downloadRequests(@RequestHeader String authorization) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        return RequestMapper.toRequestDtoList(customRequestRepository.findRequestsForProfile(profile));
    }

    @PostMapping("/accept")
    public void acceptRequest(@RequestHeader String authorization, @RequestBody UUID id) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(id, profile);
        Profile fromProfile = actualRequest.getFromProfile();
        if (actualRequest.getRequestType().equals(RequestType.FOLLOW)) {
            Friends friends = new Friends();
            friends.setProfile1(profile);
            friends.setProfile2(fromProfile);
            friendsRepository.save(friends);
        } else if (actualRequest.getRequestType().equals(RequestType.MEET)) {
            //TODO
        } else if (actualRequest.getRequestType().equals(RequestType.RADIUS)) {
            Friends friends = customFriendsRepository.findFriendsByBoth(profile, fromProfile);
            friends.setRadius(actualRequest.getRadius());
            friendsRepository.save(friends);
        }
        requestRepository.delete(actualRequest);
    }

    @DeleteMapping
    public void deleteRequest(@RequestHeader String authorization, @RequestBody UUID id) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(id, profile);
        requestRepository.delete(actualRequest);
        //TODO Exception handeln
    }

}
