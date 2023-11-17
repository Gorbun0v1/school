//package ru.hogwarts.school.service;
//
//public class AvatarService {
//    private final AvatarService avatarService;
//    public AvatarService(AvatarService avatarService) {
//        this.avatarService = avatarService;
//    }
//
//    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
//        Student student = studentRepository.getById(studentId);
//        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//        try (
//                InputStream is = avatarFile.getInputStream();
//                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//        Avatar avatar = findAvatar(studentId);
//        avatar.setStudent(student);
//        avatar.setFilePath(filePath.toString());
//        avatar.setFileSize(avatarFile.getSize());
//        avatar.setMediaType(avatarFile.getContentType());
//        avatar.setData(avatarFile.getBytes());
//        avatarRepository.save(avatar);
//    }
//    private String getExtensions(String fileName) {
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//
//    }



//}
