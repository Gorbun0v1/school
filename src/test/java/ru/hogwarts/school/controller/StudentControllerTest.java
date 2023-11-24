package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
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
        Faculty faculty = new Faculty();
        faculty.setId(52L);
        faculty.setColor("Red");
        faculty.setName("Griffindor");
        student = new Student();
        student.setAge(12);
        student.setName("Ra");
        student.setFaculty(faculty);
    }

    @Test
    void createStudent() {//post - внесение
        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port + "/create/", student, Student.class);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void getStudentById() {//get - запрос на получение
        Assertions //утверждаю что
                .assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/get/" + 1, Student.class))
                .isNotNull();
    }

    @Test
    void updateStudent() {
        student.setId(1L);
//        student.setName("Ra");
        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port + "/update/", student, Student.class);
        assertEquals(student, response.getBody());
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