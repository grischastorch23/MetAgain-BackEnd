package com.metagain.backend.mapper;

import com.metagain.backend.model.Restriction;
import com.metagain.backend.rest.data.RestrictionDto;

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
