package com.example.demowithtests.web.passportController;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.passport.PassportRequest;
import com.example.demowithtests.dto.passport.PassportResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PassportController {
    PassportResponse createPass(PassportRequest request);

    PassportResponse readPass(@PathVariable Integer id);

    PassportResponse updatePass(@PathVariable Integer id, PassportRequest request);

    List<Passport> getHistory(@PathVariable Integer id);

    void deletePass(@PathVariable Integer id);
}
