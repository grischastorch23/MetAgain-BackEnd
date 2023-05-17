package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.model.Request;
import com.example.demo.model.RequestType;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RequestRepository requestRepository;

    @PostMapping("/follow")
    public void requestFollow(@RequestHeader String authorization, @RequestParam String toUsername) {
        String fromUsername = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Request followRequest = new Request();
        followRequest.setFromProfile(profileRepository.findByUsername(fromUsername));
        followRequest.setToProfile(profileRepository.findByUsername(toUsername));
        followRequest.setRequestType(RequestType.FOLLOW);
        requestRepository.save(followRequest);
    }



}
