package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

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
    private Faculty faculty;

    @BeforeEach
    public void Setup() {
        faculty = new Faculty();
        faculty.setId(52L);
        faculty.setColor("Red");
        faculty.setName("Griffindor");
        student = new Student();
        student.setAge(12);
        student.setName("Ar");
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
        Assertions //утверждаю что
                .assertThat(response).isNotNull();
    }

    @Test
    void filterStudentsByAge() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setAge(12);
        student1.setName("Ar");
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setId(1L);
        faculty.setColor("Red");
        student1.setFaculty(faculty);
        List<Student> filter = exchangeAsList("http://localhost:" + port + "/student/filter?age=12", new ParameterizedTypeReference<>() {});
        assertEquals(List.of(student1), filter);
    }
    public <T> List<T> exchangeAsList(String uri, ParameterizedTypeReference<List<T>> responseType) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, responseType).getBody();
    }

    @Test
    void filterFindByAgeBetween() {
        ResponseEntity<List<Student>> filterByAgeBetween = restTemplate.exchange("http://localhost:" + port + "/student/filterAge/11/15", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        List<Student> students = filterByAgeBetween.getBody();
        student.setId(1L);
        assertEquals(List.of(student), students);
        assertEquals(HttpStatus.OK, filterByAgeBetween.getStatusCode());

    }

    @Test
    void getFacultyByStudentId() {
        ResponseEntity<Faculty> facultyResponseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/student/faculty/1", Faculty.class);
        assertEquals(faculty, facultyResponseEntity.getBody());
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
    }
}