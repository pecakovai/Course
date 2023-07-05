package com.example.courses_finki.repository.file;

import com.example.courses_finki.entity.file.FileEntity;
import com.example.courses_finki.entity.file.FileType;
import com.example.courses_finki.entity.subject.SubjectEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    @Transactional
    List<FileEntity> findAllBySubjectAndFileType(SubjectEntity subject, FileType type);
}
