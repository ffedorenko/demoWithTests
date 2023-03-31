package com.example.demowithtests.service.passport;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.ResourceNotAvailableException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Slf4j
@Service
public class PassportServiceBean implements PassportService {
    private final PassportRepository passportRepository;

    @Override
    public Passport create(Passport passport) {
        return passportRepository.save(passport);
    }

    @Override
    public void removeById(Integer id) {
        Passport passportToDelete = passportRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        passportToDelete.setIsDeleted(Boolean.TRUE);
        passportRepository.save(passportToDelete);
    }

    @Override
    public Passport updateById(Integer id, Passport passport) {
        return passportRepository.findById(id)
                .map(newPass -> {
                    newPass.setName(passport.getName());
                    newPass.setDateOfBirthday(passport.getDateOfBirthday());
                    return passportRepository.save(newPass);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Passport getById(Integer id) {
        Passport passport = passportRepository.findByIdAndIsDeletedIsFalse(id);
        if (passport == null) throw new ResourceNotAvailableException("Passport was deleted");
        return passport;
    }

    @Override
    public void createPassports() {
        List<Passport> passportList = Stream.generate(() -> Passport.builder()
                        .isFree(Boolean.TRUE)
                        .isDeleted(Boolean.FALSE)
                        .serialNumber(UUID.randomUUID().toString())
                        .build())
                .limit(10)
                .collect(Collectors.toList());
        passportRepository.saveAll(passportList);
    }

    @Override
    public Passport getFree() {
        Passport freePassport = passportRepository.findFirstByIsFreeIsTrueAndIsDeletedIsFalse();
        if (freePassport == null) {
            createPassports();
            return getFree();
        }
        return freePassport;
    }
}
