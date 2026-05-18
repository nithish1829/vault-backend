package com.example.vaultapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.vaultapp.entity.FileData;
import com.example.vaultapp.repository.FileRepository;
import com.example.vaultapp.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {
	
    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository repo;

    @PostMapping("/upload")
    public FileData upload(@RequestParam("file") MultipartFile file,
                           @RequestParam Long userId) throws IOException {

        String path = fileService.upload(file);

        FileData data = new FileData();
        data.setUserId(userId);
        data.setFileUrl(path);

        return repo.save(data);
    }

    @GetMapping("/{userId}")
    public List<FileData> getFiles(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteFile(@PathVariable Long id) {

        FileData file = repo.findById(id).orElseThrow();

        String path = System.getProperty("user.dir")
                + "/uploads/"
                + file.getFileUrl();

        File physicalFile = new File(path);

        if (physicalFile.exists()) {

            physicalFile.delete();

        }

        repo.delete(file);

        return "File restored successfully";
    }
}


