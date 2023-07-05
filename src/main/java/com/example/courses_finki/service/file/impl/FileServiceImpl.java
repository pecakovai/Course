package com.example.courses_finki.service.file.impl;

import com.example.courses_finki.entity.file.FileEntity;
import com.example.courses_finki.entity.file.FileType;
import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.repository.file.FileRepository;
import com.example.courses_finki.repository.subject.SubjectRepository;
import com.example.courses_finki.service.file.FileService;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final SubjectRepository subjectRepository;


    public FileServiceImpl(FileRepository fileRepository, SubjectRepository subjectRepository) {
        this.fileRepository = fileRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    @Override
    public void saveFile(String fileName, MultipartFile file, FileType fileType, Long id) throws Exception {
        SubjectEntity subject = subjectRepository.findById(id).orElseThrow(Exception::new);
        fileRepository.save(new FileEntity(fileName, file.getContentType(), file.getBytes(), fileType, subject));
    }


    @Transactional
    @Override
    public List<FileEntity> getFilesLecture(Long id) throws Exception {
        SubjectEntity subject = subjectRepository.findById(id).orElseThrow(Exception::new);
        return fileRepository.findAllBySubjectAndFileType(subject, FileType.LECTURE);
    }

    @Transactional
    @Override
    public List<FileEntity> getFilesExercise(Long id) throws Exception {
        SubjectEntity subject = subjectRepository.findById(id).orElseThrow(Exception::new);
        return fileRepository.findAllBySubjectAndFileType(subject, FileType.EXERCISE);
    }

    @Transactional
    @Override
    public FileEntity findById(Long id) throws Exception {
        return fileRepository.findById(id).orElseThrow(Exception::new);
    }


}
