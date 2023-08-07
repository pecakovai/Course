package com.example.courses_finki.data;

import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.entity.user.UserRole;
import com.example.courses_finki.repository.subject.SubjectRepository;
import com.example.courses_finki.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;

@Component
public class DataInitializr {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;


    public DataInitializr(UserRepository userRepository, SubjectRepository subjectRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        UserEntity user1 = new UserEntity("admin", "admin", "admin", passwordEncoder.encode("admin"), UserRole.ADMIN);
        UserEntity user2 = new UserEntity("Student1", "Surname1", "222222", passwordEncoder.encode("pw2"), UserRole.STUDENT);
        UserEntity user3 = new UserEntity("Student2", "Surname2", "333333", passwordEncoder.encode("pw3"), UserRole.STUDENT);
        UserEntity user4 = new UserEntity("Professor1", "Professor Surname1", "prof1", passwordEncoder.encode("prof1"), UserRole.PROFESSOR);
        UserEntity user5 = new UserEntity("Professor2", "Professor Surname2", "prof2", passwordEncoder.encode("prof2"), UserRole.PROFESSOR);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        SubjectEntity subject1 = new SubjectEntity("Operating Systems - 2022/2023",true,0.0);
        SubjectEntity subject2 = new SubjectEntity("Databases - 2022/2023",false,50.0);
        SubjectEntity subject3 = new SubjectEntity("Web Programming - 2022/2023",false,65.0);

        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        subjectRepository.save(subject3);
    }
}
