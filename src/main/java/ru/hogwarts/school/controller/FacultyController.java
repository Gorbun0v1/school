package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/create/")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty ) {
        Faculty faculty1 = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(faculty1);
    }

    @GetMapping("/get/{facultyId}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable("facultyId") Long facultyId) {
        Faculty faculty = facultyService.readFaculty(facultyId);
        if(faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/delete/{facultyId}")
    public ResponseEntity<Faculty> deleteFacultyById(@PathVariable("facultyId") Long facultyId) {
        Faculty faculty = facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok(faculty);
    }
    @PutMapping("/update")
    public ResponseEntity<Faculty> deleteFacultyById(@RequestBody Faculty faculty) {
        Faculty faculty1 = facultyService.updateFaculty(faculty);
        return ResponseEntity.ok(faculty1);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Faculty>> filterFacultiesByColor(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.filterFacultiesByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/filterColorOrName")
    public ResponseEntity<List<Faculty>> findByNameIgnoreCaseOrColorIgnoreCase(@RequestParam(value = "color", required = false) String color, @RequestParam(value = "name", required = false) String name) {

        return ResponseEntity.ok(facultyService.findByColorOrName(color, name));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Set<Student>> getAllStudentsByFacultyId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(facultyService.getStudents(id));
    }
}
