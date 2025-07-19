package com.huy.backendnoithat.mapper;

import com.huy.backendnoithat.entity.sheet.PhongCachNoiThatEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PhongCachNoiThatEntityDTOMapper {
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
    })
    PhongCachNoiThatEntity toEntity(PhongCach phongCach);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
    })
    PhongCach toDTO(PhongCachNoiThatEntity phongCachNoiThatEntity);
}
