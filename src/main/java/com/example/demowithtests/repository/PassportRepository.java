package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {
    Passport findByIdAndIsDeletedIsFalse(Integer id);
    Passport findFirstByIsFreeIsTrueAndIsDeletedIsFalse();

    @Query(value = "WITH RECURSIVE passports_ AS (\n" +
            "SELECT * FROM passport\n" +
            "         WHERE id = 14\n" +
            "         UNION ALL SELECT p.*\n" +
            "                   FROM passport p\n" +
            "                       INNER JOIN passport p_ ON p.id = p_.previous_passport_id)\n" +
            "SELECT * FROM passports_;", nativeQuery = true)
    List<Passport> getPassportHistory(Integer id);
}
