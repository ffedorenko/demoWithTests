package com.example.demowithtests.web.passportController;

import com.example.demowithtests.dto.passport.PassportRequest;
import com.example.demowithtests.dto.passport.PassportResponse;
import com.example.demowithtests.service.passport.PassportService;
import com.example.demowithtests.util.config.mapper.PassportMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassportControllerBean implements PassportControllerSwagger {

    private final PassportService passportService;
    private final PassportMapper passportMapper;

    @Override
    @PostMapping("/passes")
    @ResponseStatus(HttpStatus.CREATED)
    public PassportResponse createPass(PassportRequest request) {
        var passport = passportService.create(passportMapper.toPassport(request));
        return passportMapper.toResponseDto(passport);
    }

    @Override
    @GetMapping("/passes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassportResponse readPass(@PathVariable Integer id) {
        var passport = passportService.getById(id);
        return passportMapper.toResponseDto(passport);
    }

    @Override
    @PutMapping("/passes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassportResponse updatePass(@PathVariable Integer id, PassportRequest request) {
        var passport = passportService.updateById(id, passportMapper.toPassport(request));
        return passportMapper.toResponseDto(passport);
    }

    @Override
    @DeleteMapping("/passes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePass(@PathVariable Integer id) {
        passportService.removeById(id);
    }
}
