package com.example.courses_finki.service.user;

import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;

import java.util.List;

public interface UserService {

    public List<SubjectEntity> getUserSubjects() throws Exception;

    UserEntity findByUsername(String username);
    UserEntity findById(Long id);
}
