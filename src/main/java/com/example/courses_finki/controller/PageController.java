package com.example.courses_finki.controller;

import com.example.courses_finki.repository.user.UserRepository;
import com.example.courses_finki.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String getIndexPage(Model model) throws Exception {
        model.addAttribute("subjects", userService.getUserSubjects());
        model.addAttribute("flag", false);
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
