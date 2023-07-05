package com.example.courses_finki.controller.user;

import com.example.courses_finki.entity.user.UserRole;
import com.example.courses_finki.service.professor.ProfessorService;
import com.example.courses_finki.service.subject.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProfessorController {

    private final ProfessorService professorService;
    private final SubjectService subjectService;

    public ProfessorController(ProfessorService professorService, SubjectService subjectService) {
        this.professorService = professorService;
        this.subjectService = subjectService;
    }

    @GetMapping("/professors")
    public String getProfessorPage(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        return "professor/professor";
    }

    @GetMapping("/add/professor")
    public String getAddProfessorPage() {
        return "professor/form";
    }

    @GetMapping("/edit/professor/{id}")
    public String getEditProfessorPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("professor", professorService.findById(id));
        return "professor/form";
    }

    @GetMapping("/enroll/professor")
    public String getEnrollProfessorPage(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "enroll/enroll_professor";
    }

    @PostMapping("/add/professor")
    public String addProfessor(@RequestParam(name = "firstName") String firstName,
                               @RequestParam(name = "lastName") String lastName,
                               @RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password) {
        professorService.addProfessor(firstName, lastName, username, password, UserRole.PROFESSOR);
        return "redirect:/professors";
    }

    @PostMapping("/edit/professor/{id}")
    public String editProfessor(@PathVariable(name = "id") Long id,
                                @RequestParam(name = "firstName") String firstName,
                                @RequestParam(name = "lastName") String lastName,
                                @RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password) throws Exception {
        professorService.editProfessor(id, firstName, lastName, username, password, UserRole.PROFESSOR);
        return "redirect:/professors";
    }

    @PostMapping("/delete/professor/{id}")
    public String deleteProfessor(@PathVariable(name = "id") Long id) {
        professorService.deleteProfessor(id);
        return "redirect:/professors";
    }

    @PostMapping("/enroll/professor")
    public String enrollProfessor(@RequestParam(name = "professors") List<Long> professors, @RequestParam(name = "subjects") List<Long> subjects) {
        professorService.enrollProfessor(professors, subjects);
        return "redirect:/index";
    }
}
