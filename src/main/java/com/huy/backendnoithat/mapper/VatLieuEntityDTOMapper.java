package com.huy.backendnoithat.mapper;

import com.huy.backendnoithat.entity.sheet.VatLieuEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ThongSoEntityDTOMapper.class})
public interface VatLieuEntityDTOMapper {
    // Define mapping methods here if needed
    // For example, you can map VatLieuEntity to VatLieuDTO and vice versa
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(target = "thongSo", source = "thongSoEntity")
    })
     VatLieu toDTO(VatLieuEntity vatLieuEntity);

     @Mappings({
         @Mapping(source = "id", target = "id"),
         @Mapping(source = "name", target = "name"),
         // Add other mappings as necessary
     })
     VatLieuEntity toEntity(VatLieu vatLieuDTO);
}
