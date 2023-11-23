package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Avatar;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createStudent() {


    }

    @Test
    void getStudentById() {


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