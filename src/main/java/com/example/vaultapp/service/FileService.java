package com.example.vaultapp.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    /*public String upload(MultipartFile file) throws IOException {

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String path = uploadDir + file.getOriginalFilename();
        file.transferTo(new File(path));

        return path;
    }*/
    
    public String upload(MultipartFile file) throws IOException {

//        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File destination = new File(uploadDir + fileName);

        file.transferTo(destination);

        return fileName;
    }
}