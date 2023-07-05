package com.example.courses_finki.service.student;

import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.entity.user.UserRole;

import java.util.List;

public interface StudentService {

    public void addStudent(String firstName, String lastName, String username, String password, UserRole role);

    public void editStudent(Long id, String firstName, String lastName, String username, String password, UserRole role) throws Exception;

    public void deleteStudent(Long id);

    public void enrollStudent(List<Long> students, List<Long> subjects);

    public List<UserEntity> getStudents();

    public UserEntity findById(Long id) throws Exception;

}
