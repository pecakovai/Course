package com.example.courses_finki.controller.stripe;

import com.example.courses_finki.entity.cart.CoursesCart;
import com.example.courses_finki.entity.stripe.Request;
import com.example.courses_finki.entity.subject.SubjectEntity;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.service.cart.CoursesCartService;
import com.example.courses_finki.service.subject.SubjectService;
import com.example.courses_finki.service.user.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller

public class StripePaymentController {

    private final SubjectService subjectService;
    private final UserService userService;
    private final CoursesCartService coursesCartService;
    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @Value("${stripe.api.secretKey}")
    private String secretKey;

    public StripePaymentController(SubjectService subjectService, UserService userService, CoursesCartService coursesCartService) {
        this.subjectService = subjectService;
        this.userService = userService;
        this.coursesCartService = coursesCartService;
    }


    @GetMapping("/payment/{id}")
    public String showCard(@PathVariable Long id,

                           Model model, Authentication authentication) throws Exception {

        SubjectEntity subject = this.subjectService.findById(id);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity user = this.userService.findByUsername(userDetails.getUsername());

        Request r = new Request((long) subject.getPrice(), user.getEmail(), List.of(subject.getName()));

        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", r.getAmount());
        model.addAttribute("email", r.getEmail());
        model.addAttribute("productName", r.getProductNameAsString());
        model.addAttribute("productId", subject.getId());
        model.addAttribute("userId", user.getId());
        return "stripe/checkout";
    }


    @GetMapping("/payment/all")
    public String showCard(

                           Model model, Authentication authentication) throws Exception {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity user = this.userService.findByUsername(userDetails.getUsername());
        CoursesCart coursesCart = this.coursesCartService.getActiveCoursesCart(user.getUsername());
        Long amount = (long) coursesCart.getSubjects().stream().mapToDouble(SubjectEntity::getPrice).sum();
        List<String> subjectNames = coursesCart.getSubjects().stream()
                .map(SubjectEntity::getName)
                .collect(Collectors.toList());

        List<Long> subjectIds = coursesCart.getSubjects().stream()
                .map(SubjectEntity::getId)
                .collect(Collectors.toList());

        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", amount);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("productName", subjectNames);
        model.addAttribute("productId", subjectIds);
        model.addAttribute("userId", user.getId());
        return "stripe/checkout";
    }

    @GetMapping("/payments")
    @PreAuthorize("hasRole('ADMIN')")

    public String getSuccessfulPayments(Model model) throws StripeException {
        Stripe.apiKey = secretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 100); // Adjust the limit as needed

        PaymentIntentCollection paymentIntents = PaymentIntent.list(params);
        model.addAttribute("payments", paymentIntents.getData().stream().filter(p ->
                p.getStatus().equals("succeeded") && p.getReceiptEmail() != null && !p.getReceiptEmail().isEmpty()));
        model.addAttribute("paymentss",paymentIntents.getData());
        return "stripe/payments";
    }
}
