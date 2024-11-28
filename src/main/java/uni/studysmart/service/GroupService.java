package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Group;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.request.GroupRequest;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public Group addGroup(GroupRequest groupRequest) {
        Group group = new Group();
        group.setName(groupRequest.getName());
        return groupRepository.save(group);
    }

    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return ResponseEntity.ok(groups);
    }
}
