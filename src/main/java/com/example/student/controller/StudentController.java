package com.example.student.controller;

import java.util.List;

import com.example.student.entities.Student;
import com.example.student.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> getStudents() {
        return this.studentService.getStudents();
    }

    @GetMapping("/students/{studentID}")
    public Student getStudent(@PathVariable String studentID) {
        return this.studentService.getStudent(Integer.parseInt(studentID));
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return this.studentService.addStudent(student);
    }

    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student student) {
        return this.studentService.updateStudent(student);
    }

    @DeleteMapping("/students/{studentID}")
    public void deleteStudent(@PathVariable String studentID) {
        this.studentService.deleteStudent(Integer.parseInt(studentID));
    }

    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        ModelAndView obj = new ModelAndView("newstudent");
        return obj;
    }

    @PostMapping("/saveStudent")
    public ModelAndView saveStudent(@ModelAttribute("student") Student student) {
        studentService.addStudent(student);
        ModelAndView obj = new ModelAndView("redirect:/");
        return obj;
    }

    @GetMapping("/showForm/{id}")
    public ModelAndView showForm(@PathVariable (value = "id") int id, Model model) {
        Student student = studentService.getStudent(id);
        model.addAttribute("student", student);
        ModelAndView obj = new ModelAndView("updatestudent");
        return obj;
    }

    @GetMapping("/deleteStudent/{id}")
    public ModelAndView deleteStudent(@PathVariable (value="id") int id) {
        this.studentService.deleteStudent(id);
        ModelAndView obj = new ModelAndView("redirect:/");
        return obj;
    }

}
