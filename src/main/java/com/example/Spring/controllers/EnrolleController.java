package com.example.Spring.controllers;

import com.example.Spring.models.Enrollee;
import com.example.Spring.models.Exam;
import com.example.Spring.service.EnrolleeService;
import com.example.Spring.service.ExamService;
import com.example.Spring.database.EnrolleeDBDao;
import com.example.Spring.database.ExamDBDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class EnrolleController {
    int currentId;
    int idExam;
    EnrolleeService enrolleBDService;

    {
        try {
            enrolleBDService = new EnrolleeService(new EnrolleeDBDao());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    ExamService examDBService;

    {
        try {
            examDBService = new ExamService(new ExamDBDao());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/enrollees")
    public String enrollees(Model model) {
        model.addAttribute("title", "Список абитуриентов");
        List<Enrollee> enrollees = enrolleBDService.getAllEnrolles();
        model.addAttribute("enrollees", enrollees);
        return "enrollees";
    }

    @GetMapping("/enrollee/{id}")
    public String enrollee(@PathVariable int id, Model model) {
        Enrollee enrollee = enrolleBDService.getAllEnrolles().get(id);
        model.addAttribute("enrollee", enrollee);
        List<Exam> exams = examDBService.getExamsByEnrolleeId(enrollee.getId());
        model.addAttribute("exams", exams);
        return "enrollee";
    }

    @GetMapping("/add")
    public String enrolleeForm(Model model) {
        model.addAttribute("title", "Добавление абитуриента");
        Enrollee enrollee = new Enrollee();
        model.addAttribute("enrollee", enrollee);
        model.addAttribute("number", enrolleBDService.getAllEnrolles().size());
        return "add";
    }

    @PostMapping("/add")
    public String enrolleeSubmit(@ModelAttribute Enrollee enrollee, Model model) {
        if (enrollee.notNull()) {
            currentId = enrolleBDService.getAllEnrolles().size();
            enrollee.setId(currentId);
            enrolleBDService.save(enrollee);
            List<Enrollee> enrollees = enrolleBDService.getAllEnrolles();
            model.addAttribute("enrollees", enrollees);
        }
        return "redirect:/enrollees";
    }

    @GetMapping("/exam/{id}")
    public String examForm(@PathVariable int id, Model model) {
        idExam = id;
        model.addAttribute("title", "Добавление экзамена'");
        List<String> subjects = new ArrayList<>();
        subjects.add("Русский язык");
        subjects.add("Математика");
        subjects.add("Физика");
        subjects.add("Информатика");
        model.addAttribute("subjects", subjects);
        Exam exam = new Exam();
        model.addAttribute("exam", exam);
        model.addAttribute("id", id);
        return "exam";
    }

    @PostMapping("/exam")
    public String examSubmit(@ModelAttribute Exam exam, Model model) {
        if (exam != null)  {
            exam.setIdEnrollee(idExam);
            examDBService.save(exam);
            model.addAttribute("exam", exam);
        }
        return "redirect:/enrollees";
    }
}
