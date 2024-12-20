package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.GroupDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Course;
import uni.studysmart.model.Group;
import uni.studysmart.model.user.Student;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public GroupService(GroupRepository groupRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Long addGroup(GroupDTO groupDTO) {
        Group group = convertToEntity(groupDTO);
        group = groupRepository.save(group);
        return group.getId();
    }

    public GroupDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Group not found"));
        return convertToDTO(group);
    }

    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }

    private GroupDTO convertToDTO(Group group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getStudents() != null ? group.getStudents().stream().map(Student::getId).collect(Collectors.toList()) : null,
                group.getCourses() != null? group.getCourses().stream().map(Course::getId).collect(Collectors.toList()) : null
        );
    }

    private Group convertToEntity(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());

        List<Student> students = (groupDTO.getStudentIdList() != null)
                ? groupDTO.getStudentIdList().stream()
                .map(studentId -> studentRepository.findById(studentId)
                        .orElseThrow(() -> new ApiRequestException("Student with ID " + studentId + " not found")))
                .collect(Collectors.toList())
                : List.of();
        group.setStudents(students);

        List<Course> courses = (groupDTO.getCourseIdList() != null)
                ? groupDTO.getCourseIdList().stream()
                .map(courseId -> courseRepository.findById(courseId)
                        .orElseThrow(() -> new ApiRequestException("Course with ID " + courseId + " not found")))
                .collect(Collectors.toList())
                : List.of();
        group.setCourses(courses);
        students.forEach(student -> student.setGroup(group));

        return group;
    }


    public Long updateGroup(Long id, GroupDTO groupDTO) {
        Group existingGroup = groupRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Group with ID " + id + " not found"));

        existingGroup.setName(groupDTO.getName());

        List<Long> studentIdList = groupDTO.getStudentIdList() != null ? groupDTO.getStudentIdList() : List.of();

        List<Student> students = studentIdList.stream()
                .map(studentId -> studentRepository.findById(studentId)
                        .orElseThrow(() -> new ApiRequestException("Student with ID " + studentId + " not found")))
                .collect(Collectors.toList());

        List<Long> courseIdList = groupDTO.getCourseIdList() != null ? groupDTO.getCourseIdList() : List.of();
        List<Course> courses = courseIdList.stream()
                        .map(courseId -> courseRepository.findById(courseId)
                                .orElseThrow(() -> new ApiRequestException("Course with ID " + courseId + " not found")))
                .collect(Collectors.toList());

        existingGroup.setStudents(students);
        existingGroup.setCourses(courses);
        students.forEach(student -> student.setGroup(existingGroup));
        Group updatedGroup = groupRepository.save(existingGroup);
        return updatedGroup.getId();
    }
}
