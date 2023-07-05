package com.example.courses_finki.service.professor;

import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.entity.user.UserRole;

import java.util.List;

public interface ProfessorService {

    public void addProfessor(String firstName, String lastName, String username, String password, UserRole role);

    public void editProfessor(Long id, String firstName, String lastName, String username, String password, UserRole role) throws Exception;

    public void deleteProfessor(Long id);

    public void enrollProfessor(List<Long> professors, List<Long> subjects);

    public List<UserEntity> getProfessors();

    public UserEntity findById(Long id) throws Exception;



}
