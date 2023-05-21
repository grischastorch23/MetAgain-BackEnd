package com.example.demo.mapper;

import com.example.demo.model.Restriction;
import com.example.demo.rest.data.RestrictionDto;

public class RestrictionMapper {

    public static RestrictionDto toRestrictionDto(Restriction restriction) {
        RestrictionDto restrictionDto = new RestrictionDto();
        restrictionDto.setFromUsername(restriction.getFromProfile().getUsername());
        restrictionDto.setToUsername(restriction.getToProfile().getUsername());
        restrictionDto.setId(restriction.getId());
        restrictionDto.setRestrictionType(restriction.getRestrictionType());
        return restrictionDto;
    }

}
