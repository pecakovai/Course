package com.example.courses_finki.controller.semester;

import com.example.courses_finki.entity.semester.SemesterType;
import com.example.courses_finki.service.semester.SemesterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping("/semesters")
    public String getSemesterPage(Model model) {
        model.addAttribute("semesters", semesterService.getAllSemesters());
        return "semester/semester";
    }

    @GetMapping("/add/semester")
    public String getAddSemesterPage(Model model) {
        model.addAttribute("types", SemesterType.values());
        return "semester/form";
    }

    @GetMapping("/edit/semester/{id}")
    public String getEditSemesterPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("semester", semesterService.findById(id));
        model.addAttribute("types", SemesterType.values());
        return "semester/form";
    }

    @PostMapping("/add/semester")
    public String addSemester(@RequestParam(name = "type") String type,
                              @RequestParam(name = "year") int year) {
        semesterService.addSemester(SemesterType.valueOf(type), year);
        return "redirect:/semesters";
    }

    @PostMapping("/edit/semester/{id}")
    public String editSemester(@PathVariable(name = "id") Long id,
                               @RequestParam(name = "type") String type,
                               @RequestParam(name = "year") int year) throws Exception {
        semesterService.editSemester(id, SemesterType.valueOf(type), year);
        return "redirect:/semesters";
    }

    @PostMapping("/delete/semester/{id}")
    public String deleteSemester(@PathVariable(name = "id") Long id) {
        semesterService.deleteSemester(id);
        return "redirect:/semesters";
    }

}
