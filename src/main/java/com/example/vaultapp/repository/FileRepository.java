package com.example.vaultapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vaultapp.entity.FileData;

public interface FileRepository extends JpaRepository<FileData, Long> {
    List<FileData> findByUserId(Long userId);
}
