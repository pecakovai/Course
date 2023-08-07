package com.example.courses_finki.service.cart.impl;

import com.example.courses_finki.entity.cart.CoursesCart;
import com.example.courses_finki.entity.cart.CoursesCartStatus;
import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.repository.cart.CoursesCartRepository;
import com.example.courses_finki.repository.user.UserRepository;
import com.example.courses_finki.service.cart.CoursesCartService;
import com.example.courses_finki.service.subject.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CoursesCartServiceImpl implements CoursesCartService {
    private final CoursesCartRepository coursesCartRepository;
    private final UserRepository userRepository;
    private final SubjectService subjectService;


    @Override
    public List<SubjectEntity> listAllSubjectInCoursesCart(Long cartId) throws Exception {
        if(!this.coursesCartRepository.findById(cartId).isPresent())
            throw new Exception(String.format("Cart %d doesnt exists",cartId));
        return this.coursesCartRepository.findById(cartId).get().getSubjects();
    }



    @Override
    public CoursesCart getActiveCoursesCart(String username) throws Exception {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception(String.format("User with username %d doesnt exists",username)));

        return this.coursesCartRepository
                .findByUserAndStatus(user, CoursesCartStatus.CREATED)
                .orElseGet(() -> {
                    CoursesCart cart = new CoursesCart(user);
                    return this.coursesCartRepository.save(cart);
                });

    }

    @Override
    public CoursesCart addSubjcetToCoursesCart(String username, Long subjectId) throws Exception {
        CoursesCart shoppingCart = this.getActiveCoursesCart(username);
        SubjectEntity subject = this.subjectService.findById(subjectId);

        if(shoppingCart.getSubjects()
                .stream().filter(i -> i.getId().equals(subjectId))
                .collect(Collectors.toList()).size() > 0)
            throw new Exception(String.format("Subject with id %d already exists on %s cart ",subjectId, username));
        shoppingCart.getSubjects().add(subject);
        return this.coursesCartRepository.save(shoppingCart);

    }

    @Override
    public CoursesCart removeCourseFromCart(String username, Long subjectId) throws Exception {
        CoursesCart shoppingCart = this.getActiveCoursesCart(username);
        SubjectEntity subject = this.subjectService.findById(subjectId);

        if(shoppingCart.getSubjects()
                .stream().filter(i -> i.getId().equals(subjectId))
                .collect(Collectors.toList()).size() > 0)
        shoppingCart.getSubjects().remove(subject);
        return this.coursesCartRepository.save(shoppingCart);

    }

    @Override
    public CoursesCart removeAllCoursesFromCart(String username) throws Exception {
        CoursesCart shoppingCart = this.getActiveCoursesCart(username);

       shoppingCart.getSubjects().clear();
        return this.coursesCartRepository.save(shoppingCart);

    }
}
