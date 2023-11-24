package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;
    private Student student;

    @BeforeEach
    public void Setup() {
        student = new Student();
        student.setId(1L);
        student.setAge(12);
        student.setName("Ar");
    }

    @Test
    void createStudent() {


    }

    @Test
    void getStudentById() {
        Assertions //утверждаю что
                .assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/get/" + 1, Student.class))
                .isNotNull();
    }

    @Test
    void deleteStudentById() {

    }

    @Test
    void testDeleteStudentById() {

    }

    @Test
    void filterStudentsByAge() {

    }

    @Test
    void filterFindByAgeBetween() {

    }

    @Test
    void getFacultyByStudentId() {

    }
}