package com.example.demowithtests.util.config.mapper;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.dto.cabinet.CabinetRequest;
import com.example.demowithtests.dto.cabinet.CabinetResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CabinetMapper {
    Cabinet toCabinet(CabinetResponse cabinetResponse);

    CabinetResponse toResponse(Cabinet cabinet);

    Cabinet toCabinet(CabinetRequest cabinetRequest);

    CabinetRequest toRequest(Cabinet cabinet);
}
