package ru.hogwarts.school.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import ru.hogwarts.school.model.Student;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] avatar;
    @OneToOne
    private Student student;
    public Long getId() {
        return id;
    }
    public String getFilePath() {
        return filePath;
    }
    public long getFileSize() {
        return fileSize;
    }
    public String getMediaType() {
        return mediaType;
    }
    public byte[] getAvatar() {
        return avatar;
    }
    public Student getStudent() {
        return student;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setFilePath(Path filePath) {
        this.filePath = String.valueOf(filePath);
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avatar avatar1)) return false;
        return getFileSize() == avatar1.getFileSize() && getId().equals(avatar1.getId()) && Objects.equals(getFilePath(),
                avatar1.getFilePath()) && Objects.equals(getMediaType(), avatar1.getMediaType()) &&
                Arrays.equals(getAvatar(), avatar1.getAvatar()) && getStudent().equals(avatar1.getStudent());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getFilePath(), getFileSize(), getMediaType(), getStudent());
        result = 31 * result + Arrays.hashCode(getAvatar());
        return result;
    }
}

