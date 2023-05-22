package com.example.demo.rest;

import com.example.demo.helper.AuthorizationStringSplitter;
import com.example.demo.model.Meeting;
import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import com.example.demo.repository.CustomMeetingRepository;
import com.example.demo.repository.CustomProfileRepository;
import com.example.demo.repository.MeetingRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.rest.data.MeetingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private CustomMeetingRepository customMeetingRepository;
    @Autowired
    private CustomProfileRepository customProfileRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping
    public List<Meeting> getMeetings(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        return customMeetingRepository.getMeetingsForProfile(profile);
    }

    @DeleteMapping
    public void deleteMeeting(@RequestHeader String authorization, @RequestBody UUID id) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Meeting actualMeeting = customMeetingRepository.findByIdAndProfile(id, profileRepository.findByUsername(username));
        meetingRepository.delete(actualMeeting);
        //TODO handle Exceptions
    }

    @PutMapping
    public void updateMeeting(@RequestHeader String authorization, @RequestBody MeetingDto meetingDto) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Meeting meeting = customMeetingRepository.findByIdAndProfile(meetingDto.getId(), profileRepository.findByUsername(username));
        meeting.setMeetingPoint(meetingDto.getMeetingPoint());
        meetingRepository.save(meeting);
    }
}
