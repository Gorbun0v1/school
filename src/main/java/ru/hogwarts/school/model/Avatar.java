package ru.hogwarts.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import ru.hogwarts.school.model.Student;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    @OneToOne
    private Student student;
}
