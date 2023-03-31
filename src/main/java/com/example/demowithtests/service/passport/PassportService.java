package com.example.demowithtests.service.passport;

import com.example.demowithtests.domain.Passport;

public interface PassportService {
    Passport create(Passport passport);
    void removeById(Integer id);
    Passport updateById(Integer id, Passport passport);
    Passport getById(Integer id);
    void createPassports();

    Passport getFree();
}
