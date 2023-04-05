package com.example.demowithtests.service.cabinet;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.repository.CabinetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.example.demowithtests.util.UniqueFieldChecker.isFieldNew;

@AllArgsConstructor
@Slf4j
@Service
public class CabinetServiceBean implements CabinetService {
    private final CabinetRepository cabinetRepository;

    @Override
    public Cabinet create(Cabinet cabinet) {
        return cabinetRepository.save(cabinet);
    }

    @Override
    public Cabinet readById(Integer id) {
        return cabinetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cabinet not found with id = " + id));
    }

    @Override
    public Cabinet updateById(Integer id, Cabinet cabinet) {
        log.info("updateById(Integer id, Cabinet cabinet) Service start, id - {}, cabinet - {}", id, cabinet);
        return cabinetRepository.findById(id)
                .map(entity -> {
                    if (isFieldNew(cabinet.getIsDeleted(), entity.getIsDeleted())) {
                        entity.setIsDeleted(cabinet.getIsDeleted());
                    }
                    if (isFieldNew(cabinet.getCapacity(), entity.getCapacity())) {
                        entity.setCapacity(cabinet.getCapacity());
                    }
                    log.info("updateById(Integer id, Cabinet cabinet) Service end, entity - {}", entity);
                    return entity;
                })
                .orElseThrow(() -> new EntityNotFoundException("Cabinet not found with id = " + id));
    }

    @Override
    public void deleteById(Integer id) {
        Cabinet cabinet = cabinetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cabinet not found with id = " + id));
        cabinet.setIsDeleted(Boolean.TRUE);
        cabinetRepository.save(cabinet);
    }
}
