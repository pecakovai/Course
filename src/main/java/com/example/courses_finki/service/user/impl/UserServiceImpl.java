package com.example.courses_finki.service.user.impl;

import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.repository.user.UserRepository;
import com.example.courses_finki.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SubjectEntity> getUserSubjects() {
        try {
            return userRepository.findById(getLoggedInUser().getId()).orElseThrow(Exception::new).getSubjects();
        } catch (Exception e) {
            return null;
        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return User.withUsername(user.get().getUsername()).password(user.get().getPassword()).authorities(user.get().getRole().toString()).build();
    }

    public UserEntity getLoggedInUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username).orElseThrow(Exception::new);
            }
        }
        return null;
    }

}
