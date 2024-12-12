package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.GroupDTO;
import uni.studysmart.model.Group;
import uni.studysmart.model.user.Student;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public GroupService(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
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
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return convertToDTO(group);
    }

    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }

    private GroupDTO convertToDTO(Group group) {
        return new GroupDTO(
                group.getName(),
                group.getStudents() != null ? group.getStudents().stream().map(Student::getId).collect(Collectors.toList()) : null
        );
    }

    private Group convertToEntity(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());
        List<Student> students = groupDTO.getStudentIdList().stream()
                .map(studentRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        group.setStudents(students);
        return group;
    }
}
