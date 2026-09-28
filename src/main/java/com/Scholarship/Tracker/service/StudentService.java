package com.Scholarship.Tracker.service;

import com.Scholarship.Tracker.entity.Student;
import com.Scholarship.Tracker.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Long id, Student student) {

        Student existing = studentRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setPhone(student.getPhone());
        existing.setAnnualIncome(student.getAnnualIncome());
        existing.setMarks(student.getMarks());

        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}