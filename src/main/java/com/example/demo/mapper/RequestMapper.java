package com.example.demo.mapper;

import com.example.demo.model.Request;
import com.example.demo.rest.data.RequestDto;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static RequestDto toRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setRequestType(request.getRequestType());
        requestDto.setFromProfile(ProfileMapper.toProfile(request.getFromProfile()));
        requestDto.setToProfile(ProfileMapper.toProfile(request.getToProfile()));
        requestDto.setId(request.getId());
        return requestDto;
    }

    public static Request toRequest(RequestDto requestDto) {
        Request request = new Request();
        request.setRequestType(requestDto.getRequestType());
        request.setId(requestDto.getId());
        request.setFromProfile(ProfileMapper.toProfile(requestDto.getFromProfile()));
        request.setToProfile(ProfileMapper.toProfile(requestDto.getToProfile()));
        return request;
    }

    public static List<RequestDto> toRequestDtoList(List<Request> requestList) {
        List<RequestDto> requestDtosList = new ArrayList<>();
        for (Request request : requestList) {
            requestDtosList.add(RequestMapper.toRequestDto(request));
        }
        return requestDtosList;
    }

}
