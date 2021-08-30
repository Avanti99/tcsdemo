package com.example.student.services;

import java.util.List;

import com.example.student.dao.StudentDao;
import com.example.student.entities.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    public StudentDao studentDao;

    // List<Student> list;

    public StudentServiceImpl() {
        // list= new ArrayList<>();
        // list.add(new Student(1,"Neha","Arts"));

    }

    @Override
    public List<Student> getStudents() {
        return studentDao.findAll();
    }

    @Override
    public Student getStudent(int id) {
        // Student s = null;
        // for(Student student: list) {
        //     if(student.getId() == id) {
        //         s = student;
        //         break;
        //     }
        // }

        return studentDao.getById(id);
    }

    @Override
    public Student addStudent(Student student) {
        // list.add(student);

        studentDao.save(student);
        return student;
    }

    @Override
    public Student updateStudent(Student student) {
        // list.forEach(s -> {
        //     if(s.getId() == student.getId) {
        //         s.setName(student.getName());
        //         s.setStream(student.getStream());
        //     }
        // });

        studentDao.save(student);
        return student;
    }

    @Override
    public void deleteStudent(int id) {
        // list = this.list.stream().filter(s -> s.getId() != parseInt).collect(Collectors.toList());
        
        Student entity =  studentDao.getById(id);
        studentDao.delete(entity);
    }
    
}
