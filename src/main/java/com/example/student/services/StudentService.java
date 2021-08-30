package com.example.student.services;

import java.util.List;

import com.example.student.entities.Student;

public interface StudentService {

    public List<Student> getStudents();
    public Student getStudent(int id); 
    public Student addStudent(Student student);
    public Student updateStudent(Student student);
    public void deleteStudent(int id);
}
