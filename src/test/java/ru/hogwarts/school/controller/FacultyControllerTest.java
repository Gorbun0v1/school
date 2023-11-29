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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

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
    void createFaculty() {
        ResponseEntity<Faculty> response = this.restTemplate.postForEntity("http://localhost:" + port + "/create", faculty, Faculty.class);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void getFacultyById() {
        Assertions //утверждаю что
                .assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/get/" + 52, Faculty.class))
                .isNotNull();
    }

    @Test
    void testDeleteFacultyById() {
        faculty.setId(52L);
        ResponseEntity<Faculty> response = this.restTemplate.postForEntity("http://localhost:" + port + "/update/", faculty, Faculty.class);
        Assertions //утверждаю что
                .assertThat(response).isNotNull();
    }

    @Test
    void filterFacultiesByColor() {
        Faculty faculty1 = new Faculty();
        faculty1.setId(52L);
        List<Faculty> filter = exchangeAsList("http://localhost:" + port + "/faculty/filter?color=Red", new ParameterizedTypeReference<>() {});
        assertEquals(List.of(faculty1), filter);
    }
    public <T> List<T> exchangeAsList(String uri, ParameterizedTypeReference<List<T>> responseType) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, responseType).getBody();
    }

    @Test
    void findByNameIgnoreCaseOrColorIgnoreCase() {
        ResponseEntity<List<Faculty>> filterBy = restTemplate.exchange("http://localhost:" + port + "/faculty/filterColorOrName?color=Green", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        List<Faculty> faculties = filterBy.getBody();
        faculty.setId(103L);
        faculty.setColor("Green");
        faculty.setName("wssss");
        assertEquals(List.of(faculty), faculties);
        assertEquals(HttpStatus.OK, filterBy.getStatusCode());
    }

    @Test
    void getAllStudentsByFacultyId() {
        student.setId(1L);
        ResponseEntity<Set<Student>> studentResponseEntity = restTemplate.exchange("http://localhost:" + port + "/faculty/students/52", HttpMethod.GET, null,  new ParameterizedTypeReference<>() {});
        assertEquals(Set.of(student), studentResponseEntity.getBody());
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
    }



}