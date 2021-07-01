package com.example.demo.activity;

import com.example.demo.activity.model.Activity;
import com.example.demo.activity.model.dto.ActivityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mappings({
            @Mapping(target = "name", source = "activity.employeeName"),
            @Mapping(target = "price", source = "activity.total")
    })
    ActivityDTO toDto(Activity activity);
    Activity fromDto(ActivityDTO activity);
}
