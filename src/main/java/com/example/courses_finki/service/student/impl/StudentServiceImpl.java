package com.example.courses_finki.service.student.impl;

import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.entity.user.UserRole;
import com.example.courses_finki.repository.subject.SubjectRepository;
import com.example.courses_finki.repository.user.UserRepository;
import com.example.courses_finki.service.student.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(UserRepository userRepository, SubjectRepository subjectRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addStudent(String firstName, String lastName, String username, String password, UserRole role) {
        userRepository.save(new UserEntity(firstName, lastName, username, passwordEncoder.encode(password), role));
    }

    @Override
    public void editStudent(Long id, String firstName, String lastName, String username, String password, UserRole role) throws Exception {
        UserEntity user = userRepository.findById(id).orElseThrow(Exception::new);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setRole(role);

        if (!user.getPassword().equals(password)) {
            user.setPassword(password);
        }

        userRepository.save(user);
    }

    @Override
    public void deleteStudent(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void enrollStudent(List<Long> students, List<Long> subjects) {

        List<UserEntity> studentEntities = userRepository.findAllById(students);
        List<SubjectEntity> subjectEntities = subjectRepository.findAllById(subjects);

        for (UserEntity student : studentEntities) {
            for (SubjectEntity subject : subjectEntities) {
                student.getSubjects().add(subject);
                subject.getProfessors().add(student);
                subjectRepository.save(subject);
            }
            userRepository.save(student);
        }

    }

    @Override
    public List<UserEntity> getStudents() {
        return userRepository.findAllByRole(UserRole.STUDENT);
    }

    @Override
    public UserEntity findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
    }
}
