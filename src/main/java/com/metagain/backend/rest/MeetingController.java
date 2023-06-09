package com.metagain.backend.rest;

import com.metagain.backend.helper.AuthorizationStringSplitter;
import com.metagain.backend.mapper.MeetingMapper;
import com.metagain.backend.model.Meeting;
import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import com.metagain.backend.model.types.RequestType;
import com.metagain.backend.repository.*;
import com.metagain.backend.rest.data.MeetingDto;
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

    @Autowired
    private CustomRequestRepository customRequestRepository;

    @GetMapping
    public List<MeetingDto> getMeetings(@RequestHeader String authorization) {
        String username = AuthorizationStringSplitter.splitAuthorization(authorization)[0];
        Profile profile = customProfileRepository.findProfileByUsername(username);
        return MeetingMapper.toMeetingDtoList(customMeetingRepository.getMeetingsForProfile(profile), profile);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteMeeting(@RequestHeader String authorization, @PathVariable UUID id) {
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
