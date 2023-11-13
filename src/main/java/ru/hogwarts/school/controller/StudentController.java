package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create/")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student student1 = studentService.createStudent(student);
        return ResponseEntity.ok(student1);
    }

    @GetMapping("/get/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("studentId") Long studentId) {
        Student student = studentService.readStudent(studentId);
        if(student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Student> deleteStudentById(@PathVariable("studentId") Long studentId) {
        Student student = studentService.deleteStudent(studentId);
        return ResponseEntity.ok(student);
    }
    @PutMapping("/update")
    public ResponseEntity<Student> deleteStudentById(@RequestBody Student student) {
        Student student1 = studentService.updateStudent(student);
        return ResponseEntity.ok(student1);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByAge(@RequestParam(required = false) int age) {
        if (age > 0) {
            return  ResponseEntity.ok(studentService.filterStudentsByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }




}
