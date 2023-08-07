package com.example.courses_finki.controller.stripe;

import com.example.courses_finki.entity.cart.CoursesCart;
import com.example.courses_finki.entity.dto.EnrollmentRequest;
import com.example.courses_finki.entity.stripe.Request;
import com.example.courses_finki.entity.stripe.Response;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.service.cart.CoursesCartService;
import com.example.courses_finki.service.student.StudentService;
import com.example.courses_finki.service.user.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin("http://localhost:8080")
public class StripePaymentRestController {

    private final StudentService studentService;
    private final CoursesCartService coursesCartService;
    private final UserService userService;

    public StripePaymentRestController(StudentService studentService, CoursesCartService coursesCartService, UserService userService) {
        this.studentService = studentService;
        this.coursesCartService = coursesCartService;
        this.userService = userService;
    }

    @PostMapping("/create-payment")
    public Response createPaymentIntent(@RequestBody Request request)
            throws StripeException {

        System.out.println("CREATE PAYMENT");
        System.out.println(request.getEmail());
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.getAmount() * 100L)
                        .putMetadata("productName",
                                request.getProductNameAsString())
                        .setReceiptEmail(request.getEmail())
                        .setCurrency("usd")

                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams
                                        .AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent intent =
                PaymentIntent.create(params);
        System.out.println(intent);
        return new Response(intent.getId(),
                intent.getClientSecret());
    }

    @PostMapping("/api/enroll/student")
    public ResponseEntity<String> enrollStudent(@RequestBody EnrollmentRequest enrollmentRequest) {
        this.studentService.enrollStudent(enrollmentRequest.getStudents(), enrollmentRequest.getSubjects());
        UserEntity u = this.userService.findById(enrollmentRequest.getStudents().get(0));


                enrollmentRequest.getSubjects().forEach(sub -> {
                    try {
                        this.coursesCartService.removeCourseFromCart(u.getUsername(),sub);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


        return ResponseEntity.ok("Enrollment successful");
    }
}
