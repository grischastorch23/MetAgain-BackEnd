package com.metagain.backend.rest;

import com.example.demo.exception.*;
import com.metagain.backend.exception.*;
import com.metagain.backend.helper.AuthorizationStringSplitter;
import com.metagain.backend.mapper.RequestMapper;
import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import com.metagain.backend.model.types.RequestType;
import com.example.demo.repository.*;
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
        requestRepository.save(request);
    }

    @GetMapping
    public List<RequestDto> downloadRequests(@RequestHeader String authorization) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        return RequestMapper.toRequestDtoList(customRequestRepository.findRequestsForProfile(profile));
    }


    @DeleteMapping
    public void deleteRequest(@RequestHeader String authorization, @RequestBody UUID id) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        Request actualRequest = customRequestRepository.findRequestByIdAndProfile(id, profile);
        requestRepository.delete(actualRequest);
        //TODO Exception handeln
    }

}
