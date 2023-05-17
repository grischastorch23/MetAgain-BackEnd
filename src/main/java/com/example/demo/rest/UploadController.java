package com.example.demo.rest;


import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.mapper.RequestMapper;
import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import com.example.demo.repository.CustomRequestRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.rest.data.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomRequestRepository customRequestRepository;


    @GetMapping("/requests")
    public List<RequestDto> uploadRequests(@RequestHeader String authorization) {
        Profile profile = profileRepository.findByUsername(AuthorizationStringSplitter.splitAuthorization(authorization)[0]);
        return RequestMapper.toRequestDtoList(customRequestRepository.findRequestsWithProfile(profile));
    }


}
