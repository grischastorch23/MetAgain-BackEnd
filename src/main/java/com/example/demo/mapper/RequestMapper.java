package com.example.demo.mapper;

import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.rest.data.RequestDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

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
