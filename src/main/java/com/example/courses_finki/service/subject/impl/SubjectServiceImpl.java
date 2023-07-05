package com.example.courses_finki.service.subject.impl;

import com.example.courses_finki.entity.file.FileEntity;
import com.example.courses_finki.entity.semester.SemesterEntity;
import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.repository.semester.SemesterRepository;
import com.example.courses_finki.repository.subject.SubjectRepository;
import com.example.courses_finki.service.subject.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SemesterRepository semesterRepository) {
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
    }

    @Override
    public void addSubject(String name, Long semester) throws Exception {
        SubjectEntity subject = new SubjectEntity(name);
        subject.setSemester(semesterRepository.findById(semester).orElseThrow(Exception::new));
        subjectRepository.save(subject);

    }

    @Override
    public void editSubject(Long id, String name, Long semester) throws Exception {
        SubjectEntity subject = subjectRepository.findById(id).orElseThrow(Exception::new);
        subject.setSemester(semesterRepository.findById(semester).orElseThrow(Exception::new));
        subjectRepository.save(subject);
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public SubjectEntity findById(Long id) throws Exception {
        return subjectRepository.findById(id).orElseThrow(Exception::new);
    }

}
