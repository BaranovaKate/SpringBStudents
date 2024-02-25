package by.baranova.springbootstudents.controller;


import by.baranova.springbootstudents.exception.EntityNotFoundException;
import by.baranova.springbootstudents.model.StudentDto;
import by.baranova.springbootstudents.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private static final String CONST_ATTRIBUTE = "student";
    private static final String CONST_REDIRECT = "redirect:/students";

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String findStudents(Model model) {
        final List<StudentDto> students = studentService.findStudents();

        model.addAttribute("students", students);

        return "students/list";
    }

    @GetMapping("/{id}")
    public String findStudent(Model model, @PathVariable("id") Long id) {
       try {
           final StudentDto student = studentService.findStudentById(id);
           model.addAttribute(CONST_ATTRIBUTE, student);
           return "students/page";
       }catch (EntityNotFoundException e) {
           model.addAttribute("errorMessage", e.getMessage());
           return "students/error";
       }

    }

    @GetMapping("/new")
    public String createStudent(@ModelAttribute(CONST_ATTRIBUTE) StudentDto student) {
        return "students/new";
    }

    @PostMapping("/new")
    public String handleStudentCreation(
            @Valid @ModelAttribute(CONST_ATTRIBUTE) StudentDto student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "students/new";
        }

        studentService.save(student);
        return CONST_REDIRECT;
    }

    @GetMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, Model model) {
        final StudentDto student = studentService.findStudentById(id);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final String formattedDate = student.getBirthday().format(formatter);
        model.addAttribute(CONST_ATTRIBUTE, student);
        model.addAttribute("formattedDate", formattedDate);
        return "students/update";
    }

    @PutMapping("/{id}")
    public String handleStudentUpdate(
            @PathVariable Long id,
            @Valid @ModelAttribute(CONST_ATTRIBUTE) StudentDto student,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "students/update";

        studentService.update(id, student);
        return CONST_REDIRECT;
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        final StudentDto student = studentService.findStudentById(id);
        model.addAttribute(CONST_ATTRIBUTE, student);
        return "students/delete";
    }

    @DeleteMapping("/{id}")
    public String handleStudentDelete(@PathVariable Long id) {
        studentService.deleteById(id);
        return CONST_REDIRECT;
    }
}