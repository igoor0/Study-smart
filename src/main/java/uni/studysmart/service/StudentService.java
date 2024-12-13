package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.StudentDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Group;
import uni.studysmart.model.Preference;
import uni.studysmart.model.user.Student;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public StudentService(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
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

    public Long addStudentToGroup(Long studentId, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ApiRequestException("Group with id " + groupId + " not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ApiRequestException("Student with id " + studentId + " not found"));

        group.getStudents().add(student);
        student.setGroup(group);
        groupRepository.save(group);
        studentRepository.save(student);
        return student.getId();
    }

    public Long removeStudentFromGroup(Long studentId, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ApiRequestException("Group with id " + groupId + " not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ApiRequestException("Student with id " + studentId + " not found"));
        group.getStudents().remove(student);
        student.setGroup(null);
        groupRepository.save(group);
        studentRepository.save(student);

        return student.getId();
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getIndexNumber(),
                student.getMajor(),
                student.getGroup() != null ? student.getGroup().getId() : null,
                student.getPreferences() != null ? student.getPreferences().stream().map(Preference::getId).collect(Collectors.toList()) : null
        );
    }
}
