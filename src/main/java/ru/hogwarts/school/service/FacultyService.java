package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public List<Faculty> findByName(String name) {
    return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("", name);
    }

    public List<Faculty> findByColorOrName(String color, String name) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(color, name);
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Set<Student> getStudents(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get().getStudents();
        }
            throw new RuntimeException("ID не существует");
    }
}