package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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

    @PostMapping("/create")
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
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student student1 = studentService.updateStudent(student);
        return ResponseEntity.ok(student1);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByAge(@RequestParam(value = "age", required = false) int age) {
        if (age > 0) {
            return  ResponseEntity.ok(studentService.filterStudentsByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/filterAge/{min}/{max}")
    public List<Student> filterFindByAgeBetween(@PathVariable("min") int min, @PathVariable("max") int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/faculty/{studentId}")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable("studentId") Long id) {
        return ResponseEntity.ok(studentService.getFacultyByStudentId(id));
    }



    @GetMapping("/amount")
    public ResponseEntity<Integer> getFacultyByStudentId() {
        return ResponseEntity.ok(studentService.getAmountOfStudents());
    }

    @GetMapping("/average")
    public ResponseEntity<Integer> getAverageOfStudents() {
        return ResponseEntity.ok(studentService.getAverageOfStudents());
    }
    @GetMapping("/last")
    public ResponseEntity<List<Student>> getLastOfStudents() {
        return ResponseEntity.ok(studentService.getLastOfStudents());
    }
    @GetMapping("/name-start-with-A")
    public ResponseEntity<List<Student>> getStudentNameStartWithA() {
        return ResponseEntity.ok(studentService.getStudentNameStartWithA());
    }
    @GetMapping("/get-avg-age-with-stream")
    public ResponseEntity<Double> getAVGAgeWithStream() {
        return ResponseEntity.ok(studentService.getAVGAgeWithStream());
    }

}
