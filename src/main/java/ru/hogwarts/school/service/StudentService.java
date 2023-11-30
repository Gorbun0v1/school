package ru.hogwarts.school.service;

import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.exceptionHandler.NotSupportedClassException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

@Service
public class StudentService {
    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        log.info("@Bean StudentServices is created");
    }


    private long counter = 0L;

    // Создаем CRUD-методы

    public Student createStudent(Student student) {
        log.info("method add is run");
        log.debug("Student input: " + student);

        Student createStudent = null;

        try {
            createStudent = studentRepository.save(student);
        } catch (RuntimeException e) {
            log.error("Not can upload new student. Reason: " + e.getMessage());
            throw new NotSupportedClassException("JSON not valid");
        }

        log.debug("Student " + createStudent + " upload to db");
        return createStudent;
    }

    public Student readStudent(long id) {
        log.info("method get is run");
        Student finnStudent = studentRepository.findById(id).orElseThrow(() -> {
            log.warn("method get cannot find student with id " + id);
            return new NotFoundStudentException("Нет такого студента с id " + id);
        });
        log.debug("findStudent = " + finnStudent);
        return finnStudent;
        return studentRepository.getStudentById(id);
    }

    public Student updateStudent(Student student) {
        log.info("method getAllStudent is run");

        Student finnStudent = studentRepository.findById(student.getId()).orElseThrow(() -> {
            log.warn("method update cannot find student " + student);
            return new NotFoundStudentException("Нет такого студента : " + student);
        });
        log.debug("finnStudent = " + finnStudent);
        return studentRepository.save(student);
    }

    public Student deleteStudent(long id) {
        log.info("method remove is run");

        Student student = studentRepository.findById(id);
        log.debug("Method Remove find: " + student);

        if (student != null) {
            studentRepository.deleteById(id);
            log.debug("student deleted");
        } else {
            log.warn("Student for delete not found with id " + id);
            throw new NotFoundStudentException("Student for delete not found");
        }

        return student;
    }

    public List<Student> filterStudentsByAge(int age) {
        log.info("method filterByAge is run");
        List<Student> studentByAge = studentRepository.findAllStudentByAge(age);
        log.debug("Collection<Student> =" + studentByAge);
        return studentByAge;
    }

    public List<Student> findByAgeBetween(int min, int max) {
        log.info("method filterByAgeBetween is run");
        List<Student> byAgeBetween = studentRepository.findByAgeBetween(min, max);
        log.debug("Collection<Student> =" + byAgeBetween);
        return byAgeBetween;
    }
    public Faculty getFacultyByStudentId(Long id) {
        return studentRepository.getStudentById(id).getFaculty();
    }

    public Student findById(long id) {
        return studentRepository.findById(id);
    }

    public Integer getAmountOfStudents() {
        log.info("method getAmountStudent is run");
        Integer allAmountStudents = studentRepository.getAmountOfStudents();
        log.debug("Amount students is  " + allAmountStudents);
        return allAmountStudents;
    }

    public Integer getAverageOfStudents() {
        log.info("method getAverageStudent is run");
        Integer avgAgeStudent = studentRepository.getAverageOfStudents();
        log.debug("AverageStudent students is  " + avgAgeStudent);
        return avgAgeStudent;
    }

    public List<Student> getLastOfStudents() {
        log.info("method getLastIdStudent is run");
        List<Student> fiveLastIdStudent = studentRepository.getLastOfStudents();
        log.debug("Collection<Student> =" + fiveLastIdStudent);
        return fiveLastIdStudent;

    }
    public List<Student> getStudentNameStartWithA() {
        log.info("method getStudentNameStartWithA is run");
        List<Student> all = studentRepository.findAll();
        log.debug("Collection<Student> = {}", all);

        Supplier<List<Student>> supplier = ArrayList::new;

        BiConsumer<List<Student>, Student> accumulator = List::add;

        BinaryOperator<List<Student>> combiner = (listLeft, listRight) -> {
            List<Student> result = Collections.synchronizedList(new ArrayList<>());
            result.addAll(listLeft);
            result.addAll(listRight);
            return result;
        };

        Function<List<Student>, List<Student>> finisher = Function.identity();

        List<Student> result = all.parallelStream()
                .filter(e -> e.getName().startsWith("А"))
                .collect(Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.CONCURRENT));
        log.debug("result Collection<Student> = {}", result);

        return result;
    }

    public Double getAVGAgeWithStream() {
        log.info("method getStudentNameStartWithA is run");
        List<Student> all = studentRepository.findAll();
        OptionalDouble average = all.parallelStream().mapToInt(Student::getAge).average();
        log.debug("avg result = {}", average.orElse(0d));
        return average.orElse(0d);
    }

    public void nonSynchronizePrintSixName() {
        log.info("method nonSynchronizePrintSixName is run");
        Queue<Student> all = new ArrayDeque<>(studentRepository.findAll());
        printTwoNamesNoSynchro(all, 2);
        Thread t1 = new Thread(() -> printTwoNamesNoSynchro(all, 2));
        Thread t2 = new Thread(() -> printTwoNamesNoSynchro(all, 2));
        t1.start();
        t2.start();

    }

    public void synchronizePrintSixName() {
        log.info("method synchronizePrintSixName is run");
        Queue<Student> all = new ArrayDeque<>(studentRepository.findAll());
        printTwoNamesSynchro(all, 2);
        Thread t1 = new Thread(() -> printTwoNamesSynchro(all, 2));
        Thread t2 = new Thread(() -> printTwoNamesSynchro(all, 2));
        t1.start();
        t2.start();
    }

    private synchronized void printTwoNamesSynchro(Queue<Student> queue, int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.println(queue.poll());
        }
    }

    private void printTwoNamesNoSynchro(Queue<Student> queue, int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.println(queue.poll());
        }
    }

}
