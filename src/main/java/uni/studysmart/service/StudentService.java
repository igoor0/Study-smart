package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.StudentDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Preference;
import uni.studysmart.model.user.Student;
import uni.studysmart.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Student with id " + id + " not found"));
        return convertToDTO(student);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getIndexNumber(),
                student.getMajor(),
                student.getGroup() != null ? student.getGroup().getId() : null,
                student.getPreferences() != null ? student.getPreferences().stream().map(Preference::getId).collect(Collectors.toList()) : null
        );
    }
}
