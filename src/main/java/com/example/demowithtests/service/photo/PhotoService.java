package com.example.demowithtests.service.photo;

import com.example.demowithtests.domain.Photo;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    void addPhoto(MultipartFile file, String description, Integer id) throws IOException, HttpMediaTypeNotSupportedException;
    byte[] findPhoto(Integer id);
    void deletePhoto(Integer id);
}
