package com.huy.backendnoithat.mapper;

import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SavedFileEntityDTOMapper {
    @Mappings({
        @Mapping(target = "id", source = "savedFileEntity.id"),
        @Mapping(target = "fileName", source = "savedFileEntity.fileName"),
        @Mapping(target = "uploadStatus", source = "savedFileEntity.uploadStatus"),
        @Mapping(target = "size", source = "savedFileEntity.size"),
        @Mapping(target = "updatedDate", source = "savedFileEntity.updatedDate")
    })
    SavedFileDTO toDTO(SavedFileEntity savedFileEntity);

    @Mappings({
        @Mapping(target = "fileName", source = "savedFileDTO.fileName"),
        @Mapping(target = "uploadStatus", source = "savedFileDTO.uploadStatus"),
        @Mapping(target = "id", source = "savedFileDTO.id")
    })
    SavedFileEntity toEntity(SavedFileDTO savedFileDTO);
}
