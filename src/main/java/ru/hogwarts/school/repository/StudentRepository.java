package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student getStudentById(Long id);
    Student deleteStudentById(Long id);

    List<Student> findAllStudentByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    Student findById(long id);

    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getAmountOfStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Integer getAverageOfStudents();

    @Query(value = "select * from student order by id desc limit 3", nativeQuery = true)
    List<Student> getLastOfStudents();
}
