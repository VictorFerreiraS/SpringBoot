package com.example.demo.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        } else {
            studentRepository.save(student);
        }
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student does not exist " + studentId + " does no exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        //Regular Expression
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist"));

        if (Objects.equals(student.getName(), null)) {
            throw new IllegalStateException("The new name can not be null");
        } else if (name.isEmpty()) {
            throw new IllegalStateException("The new name can not be empty");
        } else if (Objects.equals(student.getName(), name)) {
            throw new IllegalStateException("The new name can not be the equals");
        } else {
            student.setName(name);
        }

        if (Objects.equals(student.getEmail(), null)) {
            throw new IllegalStateException("The new email can not be null");
        } else if (email.isEmpty()) {
            throw new IllegalStateException("The new email can not be empty");
        } else if (!pattern.matcher(email).matches()) {
            throw new IllegalStateException("The email is not valid");
        } else if (Objects.equals(student.getEmail(), email)) {
            throw new IllegalStateException("The new email can not be the equals");
        } else {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            } else {
                student.setEmail(email);
            }
        }

    }

}