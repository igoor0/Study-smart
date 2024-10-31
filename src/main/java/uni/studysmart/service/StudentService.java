package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Student;
import uni.studysmart.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student getStudentByIndexNumber(String indexNumber) {
        return studentRepository.findByIndexNumber(indexNumber).orElse(null);
    }
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
