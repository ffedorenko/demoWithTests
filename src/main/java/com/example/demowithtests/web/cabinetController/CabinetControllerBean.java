package com.example.demowithtests.web.cabinetController;

import com.example.demowithtests.dto.cabinet.CabinetRequest;
import com.example.demowithtests.dto.cabinet.CabinetResponse;
import com.example.demowithtests.service.cabinet.CabinetService;
import com.example.demowithtests.util.config.mapper.CabinetMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Cabinet")
public class CabinetControllerBean implements CabinetControllerSwagger {
    private final CabinetService cabinetService;
    private final CabinetMapper cabinetMapper;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/cabinets")
    public CabinetResponse createCabinet(@RequestBody CabinetRequest request) {
        return cabinetMapper.toResponse(
                cabinetService.create(cabinetMapper.toCabinet(request))
        );
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/cabinets/{id}")
    public CabinetResponse readCabinet(@PathVariable Integer id) {
        return cabinetMapper.toResponse(cabinetService.readById(id));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/cabinets/{id}")
    public CabinetResponse updateCabinet(@PathVariable Integer id, @RequestBody CabinetRequest request) {
        return cabinetMapper.toResponse(
                        cabinetService.updateById(id, cabinetMapper.toCabinet(request))
                );
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cabinets/{id}")
    public void deleteCabinet(@PathVariable Integer id) {
        cabinetService.deleteById(id);
    }
}
