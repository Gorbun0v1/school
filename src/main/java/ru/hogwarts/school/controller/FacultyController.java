package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;

@Controller
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
}
