package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty getFacultyById(Long id);

    Faculty deleteFacultyById(Long id);

    List<Faculty> findAllByColor(String color);

    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String color, String name);
}
