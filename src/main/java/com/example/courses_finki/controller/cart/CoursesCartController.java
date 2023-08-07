package com.example.courses_finki.controller.cart;

import com.example.courses_finki.entity.cart.CoursesCart;
import com.example.courses_finki.entity.user.UserEntity;
import com.example.courses_finki.service.cart.CoursesCartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/courses-cart")
public class CoursesCartController {
    private final CoursesCartService coursesCartService;


    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) throws Exception {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        System.out.println(username);
        CoursesCart shoppingCart = this.coursesCartService.getActiveCoursesCart(username);
        model.addAttribute("subjects", this.coursesCartService.listAllSubjectInCoursesCart(shoppingCart.getId()));
        return "cart/cart";
    }

    @PostMapping("/add-subject/{id}")
    public String addSubjcetToCourseCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername());
            this.coursesCartService.addSubjcetToCoursesCart(userDetails.getUsername(), id);
            return "redirect:/courses-cart";
        } catch (Exception exception) {
            return "redirect:/courses-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/remove-subject/{id}")
    public String removeCoursesFromCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername());
            this.coursesCartService.removeCourseFromCart(userDetails.getUsername(), id);
            return "redirect:/courses-cart";
        } catch (Exception exception) {
            return "redirect:/courses-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/clear")
    public String removeAllCoursesFromCart( Authentication authentication) {

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            this.coursesCartService.removeAllCoursesFromCart(userDetails.getUsername());
            return "redirect:/courses-cart";
        } catch (Exception exception) {
            return "redirect:/courses-cart?error=" + exception.getMessage();
        }
    }
}

