package com.huy.backendnoithat.mapper;

import com.huy.backendnoithat.entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface HangMucEntityDTOMapper {
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
    })
    HangMucEntity toEntity(HangMuc hangMuc);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
    })
    HangMuc toDTO(HangMucEntity hangMucEntity);
}
