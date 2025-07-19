package com.huy.backendnoithat.mapper;

import com.huy.backendnoithat.entity.sheet.ThongSoEntity;
import com.huy.backendnoithat.model.dto.BangNoiThat.ThongSo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ThongSoEntityDTOMapper {
    // Define mapping methods here if needed
    // For example, you can map ThongSoEntity to ThongSoDTO and vice versa
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "dai", target = "dai"),
        @Mapping(source = "rong", target = "rong"),
        @Mapping(source = "cao", target = "cao"),
        @Mapping(source = "donVi", target = "donVi"),
        @Mapping(source = "donGia", target = "donGia")
    })
    ThongSo toDTO(ThongSoEntity thongSoEntity);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "dai", target = "dai"),
        @Mapping(source = "rong", target = "rong"),
        @Mapping(source = "cao", target = "cao"),
        @Mapping(source = "donVi", target = "donVi"),
        @Mapping(source = "donGia", target = "donGia")
    })
    ThongSoEntity toEntity(ThongSo thongSoDTO);
}
