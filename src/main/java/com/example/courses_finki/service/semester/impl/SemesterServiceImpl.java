package com.example.courses_finki.service.semester.impl;

import com.example.courses_finki.entity.semester.SemesterEntity;
import com.example.courses_finki.entity.semester.SemesterType;
import com.example.courses_finki.repository.semester.SemesterRepository;
import com.example.courses_finki.service.semester.SemesterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public void addSemester(SemesterType semesterType, int year) {
        semesterRepository.save(new SemesterEntity(semesterType, year));
    }

    @Override
    public void editSemester(Long id, SemesterType semesterType, int year) throws Exception {
        SemesterEntity semester = semesterRepository.findById(id).orElseThrow(Exception::new);
        semester.setType(semesterType);
        semester.setYear(year);
        semesterRepository.save(semester);
    }

    @Override
    public void deleteSemester(Long id) {
        semesterRepository.deleteById(id);
    }

    @Override
    public List<SemesterEntity> getAllSemesters() {
        return semesterRepository.findAll();
    }

    @Override
    public SemesterEntity findById(Long id) throws Exception {
        return semesterRepository.findById(id).orElseThrow(Exception::new);
    }
}
