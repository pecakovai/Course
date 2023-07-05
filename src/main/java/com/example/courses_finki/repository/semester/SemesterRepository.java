package com.example.courses_finki.repository.semester;

import com.example.courses_finki.entity.semester.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<SemesterEntity,Long> {
}
