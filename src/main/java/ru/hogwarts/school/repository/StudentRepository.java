package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student getStudentById(Long id);
    Student deleteStudentById(Long id);

    List<Student> findAllStudentByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    Student findById(long id);

}
