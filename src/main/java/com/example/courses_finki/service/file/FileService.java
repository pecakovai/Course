package com.example.courses_finki.service.file;

import com.example.courses_finki.entity.file.FileEntity;
import com.example.courses_finki.entity.file.FileType;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {

    @Transactional
    public void saveFile(String fileName, MultipartFile file, FileType fileType, Long id) throws Exception;

    @Transactional
    public List<FileEntity> getFilesLecture(Long id) throws Exception;

    @Transactional
    public List<FileEntity> getFilesExercise(Long id) throws Exception;

    @Transactional
    public FileEntity findById(Long id) throws Exception;
}
