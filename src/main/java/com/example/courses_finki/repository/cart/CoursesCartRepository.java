package com.example.courses_finki.repository.cart;

import com.example.courses_finki.entity.cart.CoursesCart;
import com.example.courses_finki.entity.cart.CoursesCartStatus;
import com.example.courses_finki.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursesCartRepository extends JpaRepository<CoursesCart, Long> {
    Optional<CoursesCart> findByUserAndStatus(UserEntity user, CoursesCartStatus status);
}

