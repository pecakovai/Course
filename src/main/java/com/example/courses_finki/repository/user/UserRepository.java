package com.example.courses_finki.repository.user;

import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAllByRole(UserRole role);
}
