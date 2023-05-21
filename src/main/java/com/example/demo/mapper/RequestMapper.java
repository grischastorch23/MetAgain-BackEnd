package com.example.demo.mapper;

import com.example.demo.model.Request;
import com.example.demo.rest.data.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {


    public static RequestDto toRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setRequestType(request.getRequestType());
        requestDto.setFromUsername(request.getFromProfile().getUsername());
        requestDto.setToUsername(request.getToProfile().getUsername());
        requestDto.setId(request.getId());
        return requestDto;
    }

    public static List<RequestDto> toRequestDtoList(List<Request> requestList) {
        List<RequestDto> requestDtosList = new ArrayList<>();
        for (Request request : requestList) {
            requestDtosList.add(RequestMapper.toRequestDto(request));
        }
        return requestDtosList;
    }

}
