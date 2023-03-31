package com.example.demowithtests.service.photo;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PhotoRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoServiceBean implements PhotoService {

    private final EmployeeRepository employeeRepository;
    private final PhotoRepository photoRepository;

    @Override
    public void addPhoto(MultipartFile file, String description, Integer id)
            throws IOException, HttpMediaTypeNotSupportedException {
        if (!Objects.equals(file.getContentType(), "image/jpeg"))
            throw new HttpMediaTypeNotSupportedException("photo must be in jpeg format");
        Employee employee = employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Integer newId = 0;
        for (Photo photo:
             employee.getPhotos()) {
            if (newId < photo.getId()) {
                newId = photo.getId() + 1;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        employee.getPhotos().add(Photo.builder()
                .fileName(stringBuilder
                        .append(employee.getName()).append(employee.getId()).append("_photo").append(newId).append(".jpeg").toString())
                .description(description)
                .bytes(file.getBytes())
                .isDeleted(Boolean.FALSE)
                .addDate(LocalDate.now())
                .build());
        employeeRepository.save(employee);
    }

    @Override
    public byte[] findPhoto(Integer id) {
        Photo photo = photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (photo.getIsDeleted()) {
            throw new ResourceWasDeletedException();
        }
        return photo.getBytes();
    }

    @Override
    public void deletePhoto(Integer id) {
        Photo photo = photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        photo.setIsDeleted(Boolean.TRUE);
        photoRepository.save(photo);
    }
}
