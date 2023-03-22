package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Controller {
    EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave);

    List<EmployeeReadDto> getAllUsers();

    Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size);

    EmployeeReadDto getEmployeeById(@PathVariable Integer id);

    EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto dto);

    void removeEmployeeById(@PathVariable Integer id);

    void removeAllUsers();

    Page<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder);

    List<String> getAllUsersC();

    List<String> getAllUsersSort();

    Optional<String> getAllUsersSo();

    List<EmployeeReadDto> getAllUsersByExpiredPhotos();

    void sendMail();

    EmployeeReadDto addPhotoToEmployee(@RequestParam MultipartFile file, @RequestParam String description,
                                       @PathVariable Integer id)
            throws IOException, HttpMediaTypeNotSupportedException;

    byte[] getPhoto(@PathVariable Integer id);

    void removePhoto(@PathVariable Integer id);
}
