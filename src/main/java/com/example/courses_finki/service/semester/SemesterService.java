package com.example.courses_finki.service.semester;

import com.example.courses_finki.entity.semester.SemesterEntity;
import com.example.courses_finki.entity.semester.SemesterType;

import java.util.List;

public interface SemesterService {

    public void addSemester(SemesterType semesterType, int year);

    public void editSemester(Long id, SemesterType semesterType, int year) throws Exception;

    public void deleteSemester(Long id);

    public List<SemesterEntity> getAllSemesters();

    public SemesterEntity findById(Long id) throws Exception;
}
