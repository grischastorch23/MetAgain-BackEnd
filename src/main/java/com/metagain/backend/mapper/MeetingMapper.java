package com.metagain.backend.mapper;

import com.metagain.backend.model.Meeting;
import com.metagain.backend.model.Profile;
import com.metagain.backend.rest.data.MeetingDto;

import java.util.ArrayList;
import java.util.List;

public class MeetingMapper {

    public static MeetingDto toMeetingDto(Meeting meeting, Profile profile) {
        MeetingDto meetingDto = new MeetingDto();
        Profile friendsProfile = (meeting.getProfile1().equals(profile)) ? meeting.getProfile2() : meeting.getProfile1();
        meetingDto.setProfile(ProfileMapper.toProfileDto(friendsProfile));
        meetingDto.setId(meeting.getId());
        meetingDto.setMeetingPoint(meeting.getMeetingPoint());
        return meetingDto;
    }

    public static List<MeetingDto> toMeetingDtoList(List<Meeting> meetingList, Profile profile) {
        List<MeetingDto> meetingDtoList = new ArrayList<>();
        for (Meeting meeting: meetingList) {
            meetingDtoList.add(toMeetingDto(meeting, profile));
        }
        return meetingDtoList;
    }

}
