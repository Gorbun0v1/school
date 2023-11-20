package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    private long counter = 0L;

    // Создаем CRUD-методы

    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student readStudent(long id) {
        return studentRepository.getStudentById(id);
    }

    public Student updateStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student deleteStudent(long id) {
        return studentRepository.deleteStudentById(id);
    }

    public List<Student> filterStudentsByAge(int age) {
        return studentRepository.findAllStudentByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
    public Faculty getFacultyByStudentId(Long id) {
        return studentRepository.getStudentById(id).getFaculty();
    }

    public Student findById(long id) {
        return studentRepository.findById(id);
    }
}
