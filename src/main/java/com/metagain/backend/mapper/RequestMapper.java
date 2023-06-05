package com.metagain.backend.mapper;

import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import com.metagain.backend.rest.data.RequestDto;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {



    public static RequestDto toRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setRequestType(request.getRequestType());
        requestDto.setProfile(ProfileMapper.toProfileDto(request.getFromProfile()));
        requestDto.setId(request.getId());
        requestDto.setRadius(request.getRadius());
        return requestDto;
    }

    public static List<RequestDto> toRequestDtoList(List<Request> requestList) {
        List<RequestDto> requestDtosList = new ArrayList<>();
        for (Request request : requestList) {
            requestDtosList.add(RequestMapper.toRequestDto(request));
        }
        return requestDtosList;
    }

    public static Request toRequest(RequestDto requestDto, Profile author, Profile recipient) {
        Request request = new Request();
        request.setFromProfile(author);
        request.setToProfile(recipient);
        request.setRequestType(requestDto.getRequestType());
        request.setRadius(requestDto.getRadius());
        return request;
    }


}
