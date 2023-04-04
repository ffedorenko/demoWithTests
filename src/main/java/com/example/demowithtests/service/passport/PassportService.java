package com.example.demowithtests.service.passport;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportStatus;

import java.util.List;

public interface PassportService {
    Passport create(Passport passport);
    void removeById(Integer id);
    Passport updateById(Integer id, Passport passport);
    Passport replacePassport(Passport passport, PassportStatus reason);
    List<Passport> showHistory(Integer passportId);
    Passport getById(Integer id);
    void createPassports();

    Passport getFree();
}
