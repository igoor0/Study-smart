package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.GroupDTO;
import uni.studysmart.model.Group;
import uni.studysmart.model.Student;
import uni.studysmart.repository.GroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
                group.getId(),
                group.getName(),
                group.getStudents() != null ? group.getStudents().stream().map(Student::getId).collect(Collectors.toList()) : null,
                group.getCourses() != null ? group.getCourses().getId() : null
        );
    }

    private Group convertToEntity(GroupDTO groupDTO) {
        Group group = new Group();
        group.setId(groupDTO.getId());
        group.setName(groupDTO.getName());

        return group;
    }

}
