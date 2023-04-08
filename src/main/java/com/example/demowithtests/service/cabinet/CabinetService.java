package com.example.demowithtests.service.cabinet;

import com.example.demowithtests.domain.Cabinet;

public interface CabinetService {
    Cabinet create(Cabinet cabinet);
    Cabinet readById(Integer id);
    Cabinet updateById(Integer id, Cabinet cabinet);
    void deleteById(Integer id);
    Boolean checkCabinet(Integer cabinetId);
}
