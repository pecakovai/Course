package com.example.courses_finki.controller.subject;

import com.example.courses_finki.entity.file.FileEntity;
import com.example.courses_finki.entity.file.FileType;
import com.example.courses_finki.service.file.FileService;
import com.example.courses_finki.service.semester.SemesterService;
import com.example.courses_finki.service.subject.SubjectService;
import com.example.courses_finki.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class SubjectController {

    private final SubjectService subjectService;
    private final SemesterService semesterService;
    private final FileService fileService;
    private final UserService userService;

    public SubjectController(SubjectService subjectService, SemesterService semesterService, FileService fileService, UserService userService) {
        this.subjectService = subjectService;
        this.semesterService = semesterService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/subjects")
    public String getSubjectPage(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subject/subject";
    }

    @GetMapping("/add/subject")
    public String getAddSubjectPage(Model model) {
        model.addAttribute("semesters", semesterService.getAllSemesters());
        return "subject/form";
    }

    @GetMapping("/edit/subject/{id}")
    public String getEditSubjectPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("subject", subjectService.findById(id));
        model.addAttribute("semesters", semesterService.getAllSemesters());
        return "subject/form";
    }

    @GetMapping("/view/{id}")
    public String getSubjectPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("flag", true);
        model.addAttribute("id", id);
        model.addAttribute("lectures", fileService.getFilesLecture(id));
        model.addAttribute("exercises", fileService.getFilesExercise(id));
        model.addAttribute("subjects", userService.getUserSubjects());
        return "subject/course";
    }

    @GetMapping("/add/file/lecture/{id}")
    public String getAddFileForLecturePage(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("flag", true);
        model.addAttribute("id", id);
        return "subject/add_file";
    }

    @GetMapping("/add/file/exercise/{id}")
    public String getAddFileForExercisePage(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("id", id);
        return "subject/add_file";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable(name = "id") Long id, HttpServletResponse response) throws Exception {

        FileEntity file = fileService.findById(id);

        byte[] fileContent = file.getData();
        String fileName = file.getFileName();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDispositionFormData("attachment", fileName + "." + file.getContentType().split("/")[1]);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(fileContent);
    }

    @PostMapping("/add/subject")
    public String addSubject(@RequestParam(name = "name") String name, @RequestParam(name = "semester") Long semester) throws Exception {
        subjectService.addSubject(name, semester);
        return "redirect:/subjects";
    }

    @PostMapping("/edit/subject/{id}")
    public String editSubject(@PathVariable(name = "id") Long id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "semester") Long semester) throws Exception {
        subjectService.editSubject(id, name, semester);
        return "redirect:/subjects";
    }

    @PostMapping("/delete/subject/{id}")
    public String deleteSubject(@PathVariable(name = "id") Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }

    @PostMapping("/add/file/lecture")
    public String addFileForLecture(@RequestParam(name = "id") Long id,
                                    @RequestParam(name = "fileName") String fileName,
                                    @RequestParam(name = "file") MultipartFile file) throws Exception {
        fileService.saveFile(fileName, file, FileType.LECTURE, id);
        return "redirect:/index";
    }

    @PostMapping("/add/file/exercise")
    public String addFileForExercise(@RequestParam(name = "id") Long id,
                                     @RequestParam(name = "fileName") String fileName,
                                     @RequestParam(name = "file") MultipartFile file) throws Exception {
        fileService.saveFile(fileName, file, FileType.EXERCISE, id);
        return "redirect:/index";
    }


}
