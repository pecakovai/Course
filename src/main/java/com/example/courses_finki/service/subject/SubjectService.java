package com.example.courses_finki.service.subject;

import com.example.courses_finki.entity.file.FileEntity;
import com.example.courses_finki.entity.subject.SubjectEntity;

import java.util.List;

public interface SubjectService {

    public void addSubject(String name, Long semester) throws Exception;

    public void editSubject(Long id, String name, Long semester) throws Exception;

    public void deleteSubject(Long id);

    public List<SubjectEntity> getAllSubjects();

    public SubjectEntity findById(Long id) throws Exception;




}
