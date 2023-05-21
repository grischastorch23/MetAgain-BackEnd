package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.mapper.RequestMapper;
import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import com.example.demo.model.types.RequestType;
import com.example.demo.repository.CustomRequestRepository;
import com.example.demo.repository.FriendsRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.RequestRepository;
import com.example.demo.rest.data.RequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private CustomRequestRepository customRequestRepository;

    @PostMapping("/send")
    public void sendRequest(@RequestHeader String authorization, @RequestBody @Valid RequestDto requestDto) {
        String fromUsername = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        String toUsername = requestDto.getToUsername();
        Request request = new Request();
        request.setFromProfile(profileRepository.findByUsername(fromUsername));
        request.setToProfile(profileRepository.findByUsername(toUsername));
        request.setRequestType(requestDto.getRequestType());
        request.setRadius(requestDto.getRadius());
        requestRepository.save(request);
        //TODO check if they are follower when type is meet or radius
    }

    @GetMapping
    public List<RequestDto> downloadRequests(@RequestHeader String authorization) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        return RequestMapper.toRequestDtoList(customRequestRepository.findRequestsWithProfile(profile));
    }

    @PostMapping("/accept")
    public void acceptRequest(@RequestHeader String authorization, @RequestBody @Valid RequestDto requestDto) {
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(requestDto.getId(), profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]));
        Profile fromProfile = actualRequest.getFromProfile();
        Profile toProfile = actualRequest.getToProfile();
        if (requestDto.getRequestType().equals(RequestType.FOLLOW)) {
            Friends friends = new Friends();
            friends.setProfile1(fromProfile);
            friends.setProfile2(toProfile);
            friendsRepository.save(friends);
        } else if (requestDto.getRequestType().equals(RequestType.MEET)) {
            //TODO
        } else if (requestDto.getRequestType().equals(RequestType.RADIUS)) {
            //TODO
        }
        requestRepository.delete(actualRequest);
    }

    @DeleteMapping
    public void deleteRequest(@RequestHeader String authorization, @RequestBody @Valid RequestDto requestDto) {
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(requestDto.getId(), profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]));
        requestRepository.delete(actualRequest);
    }

}
