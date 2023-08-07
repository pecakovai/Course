package com.example.courses_finki.service.cart;

import com.example.courses_finki.entity.cart.CoursesCart;
import com.example.courses_finki.entity.subject.SubjectEntity;

import java.util.List;

public interface CoursesCartService {

    List<SubjectEntity> listAllSubjectInCoursesCart(Long cartId) throws Exception;
    CoursesCart getActiveCoursesCart(String username) throws Exception;
    CoursesCart addSubjcetToCoursesCart(String username, Long subjectId) throws Exception;

    CoursesCart removeCourseFromCart(String username, Long subjectId) throws Exception;
    CoursesCart removeAllCoursesFromCart(String username)  throws Exception;


}
