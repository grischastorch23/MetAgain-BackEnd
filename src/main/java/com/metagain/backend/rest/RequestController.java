package com.metagain.backend.rest;


import com.metagain.backend.exception.*;
import com.metagain.backend.helper.AuthorizationStringSplitter;
import com.metagain.backend.mapper.FriendsMapper;
import com.metagain.backend.mapper.MeetingMapper;
import com.metagain.backend.mapper.RequestMapper;
import com.metagain.backend.model.Friends;
import com.metagain.backend.model.Meeting;
import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import com.metagain.backend.model.types.RequestType;
import com.metagain.backend.repository.*;
import com.metagain.backend.rest.data.RequestDto;
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

    @Autowired
    private CustomProfileRepository customProfileRepository;

    @Autowired
    private MeetingRepository meetingRepository;



    @PostMapping
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
        System.out.println("Bis hier");
        requestRepository.save(request);
    }

    @GetMapping
    public List<RequestDto> downloadRequests(@RequestHeader String authorization) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        return RequestMapper.toRequestDtoList(customRequestRepository.findRequestsForProfile(profile));
    }


    @DeleteMapping(path = "/{id}")
    public void deleteRequest(@RequestHeader String authorization, @PathVariable UUID id) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(id, profile);
        requestRepository.delete(actualRequest);
        //TODO Exception handeln
    }

    @PatchMapping(path = "/{id}")
    public void acceptRequest(@RequestHeader String authorization, @PathVariable UUID id) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(id, profile);
        if (actualRequest.getRequestType().equals(RequestType.FOLLOW)) {
            Friends friends = FriendsMapper.toFriends(profile, actualRequest.getFromProfile());
            friendsRepository.save(friends);
            requestRepository.delete(actualRequest);
        } else if (actualRequest.getRequestType().equals(RequestType.MEET)){
            Meeting meeting = new Meeting();
            meeting.setProfile1(profile);
            meeting.setProfile2(actualRequest.getFromProfile());
            meetingRepository.save(meeting);
        } else if (actualRequest.getRequestType().equals(RequestType.RADIUS)) {
            Friends friends = customFriendsRepository.findFriendsByBoth(profile, actualRequest.getFromProfile());
            friends.setRadius(actualRequest.getRadius());
            friendsRepository.save(friends);
        }
    }

}
