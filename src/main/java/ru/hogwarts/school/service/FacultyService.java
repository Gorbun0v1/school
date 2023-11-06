package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();

    private long counter = 0L;

    // Создаем CRUD-методы

    public Faculty createFaculty(Faculty faculty) {
       List<Faculty> faculties1 = faculties.values().stream()
                        .filter(f -> f.getColor().equals(faculty.getColor())).toList();
        if (faculties1.size() > 0) {
            throw new RuntimeException();
        }
        faculty.setId(++counter);
        faculties.put(counter, faculty);
        return faculty;
    }

    public Faculty readFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    public List<Faculty> filterFacultiesByColor (String color) {
        return faculties.values().stream()
                .filter(f -> f.getColor().equals(color))
                .toList();
    }

}
