package com.example.courses_finki.entity.cart;

import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class CoursesCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private UserEntity user;

    @ManyToMany
    private List<SubjectEntity> subjects;

    @Enumerated(EnumType.STRING)
    private CoursesCartStatus status;

    public CoursesCart() {
    }

    public CoursesCart(UserEntity user) {
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.subjects = new ArrayList<>();
        this.status = CoursesCartStatus.CREATED;
    }


}
