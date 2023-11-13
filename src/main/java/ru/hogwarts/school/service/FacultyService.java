package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    private long counter = 0L;

    // Создаем CRUD-методы

    public Faculty createFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public Faculty readFaculty(long id) {
        return facultyRepository.getFacultyById(id);
    }
    public Faculty updateFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return facultyRepository.deleteFacultyById(id);
    }

    public List<Faculty> filterFacultiesByColor (String color) {
        return facultyRepository.findAllByColor(color);
    }
}
