package by.baranova.springbootstudents.service;

import by.baranova.springbootstudents.exception.EntityNotFoundException;
import by.baranova.springbootstudents.model.StudentDto;
import by.baranova.springbootstudents.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> findStudents() {
        return studentRepository.findAll();
    }

    public StudentDto findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Journey with id " + id + " does not exist"));
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public void save(StudentDto studentDto) {
        studentRepository.save(studentDto);
    }

    public void update(Long id, StudentDto student) {
        studentRepository.update(id, student);
    }
}