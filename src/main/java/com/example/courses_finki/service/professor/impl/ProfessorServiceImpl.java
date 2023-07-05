package com.example.courses_finki.service.professor.impl;

import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.entity.user.UserRole;
import com.example.courses_finki.repository.subject.SubjectRepository;
import com.example.courses_finki.repository.user.UserRepository;
import com.example.courses_finki.service.professor.ProfessorService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorServiceImpl(UserRepository userRepository, SubjectRepository subjectRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addProfessor(String firstName, String lastName, String username, String password, UserRole role) {
        userRepository.save(new UserEntity(firstName, lastName, username, passwordEncoder.encode(password), role));
    }

    @Override
    public void editProfessor(Long id, String firstName, String lastName, String username, String password, UserRole role) throws Exception {
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
    public void deleteProfessor(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void enrollProfessor(List<Long> professors, List<Long> subjects) {
        List<UserEntity> professorsEntities = userRepository.findAllById(professors);
        List<SubjectEntity> subjectEntities = subjectRepository.findAllById(subjects);

        for (UserEntity professor : professorsEntities) {
            for (SubjectEntity subject : subjectEntities) {
                professor.getSubjects().add(subject);
                subject.getProfessors().add(professor);
                subjectRepository.save(subject);
            }
            userRepository.save(professor);
        }

    }

    @Override
    public List<UserEntity> getProfessors() {
        return userRepository.findAllByRole(UserRole.PROFESSOR);
    }

    @Override
    public UserEntity findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
    }
}
