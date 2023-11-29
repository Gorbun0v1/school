package ru.hogwarts.school.service;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptionHandler.NoContentException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Transactional
@Service
public class AvatarService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final Logger log = LoggerFactory.getLogger(AvatarService.class);
    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        log.info("@Bean AvatarService is created");
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        log.info("method uploadAvatar is run");
        Student student = studentRepository.getStudentById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW); BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);) {
            bis.transferTo(bos);
            log.debug("file saved successfully");
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        log.debug("Avatar is completed :" + avatar);
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId) {
        log.debug("Found avatar = " + findAvatar(studentId));
        log.info("method findAvatar is run");
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseThrow(() -> {
            log.warn("Student with id " + studentId + " not have an avatar");
            return new NoContentException("The student does not have an avatar");
        });
        log.debug("Student with id = " + studentId + "have the avatar with id = " + avatar.getId());

        return avatar;

    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getPage(Integer pageNumber, Integer size) {
        log.info("method getPage is run");
            if (pageNumber < 1 || size < 1) {
                log.warn("input incorrect number and size of page = " + pageNumber + ":" + size);
                throw new RuntimeException("input incorrect number and size of page.");
            }
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, size);
            return avatarRepository.findAll(pageRequest).getContent();
    }
//    private Avatar findAvatar(long studentId)
}