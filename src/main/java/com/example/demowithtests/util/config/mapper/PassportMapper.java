package com.example.demowithtests.util.config.mapper;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.passport.PassportRequest;
import com.example.demowithtests.dto.passport.PassportResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    PassportRequest toRequestDto(Passport passport);

    PassportResponse toResponseDto(Passport passport);

    Passport toPassport(PassportResponse passportResponse);

    Passport toPassport(PassportRequest passportRequest);
}
