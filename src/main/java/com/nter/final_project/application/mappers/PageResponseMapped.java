package com.nter.final_project.application.mappers;

import com.nter.final_project.presentation.dto.PageResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageResponseMapped {

    //OUPUT
    PageResponse toDto(Page page);
}
