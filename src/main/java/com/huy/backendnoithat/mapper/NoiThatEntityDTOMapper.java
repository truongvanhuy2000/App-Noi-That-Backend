package com.huy.backendnoithat.mapper;

import com.huy.backendnoithat.entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NoiThatEntityDTOMapper {
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
    })
    NoiThatEntity toEntity(NoiThat noiThat);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
    })
    NoiThat toDTO(NoiThatEntity noiThatEntity);
}
