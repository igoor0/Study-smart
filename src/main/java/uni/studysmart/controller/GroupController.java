package uni.studysmart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.GroupDTO;
import uni.studysmart.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groupDTOList = groupService.getAllGroups();
        return ResponseEntity.ok(groupDTOList);
    }

    @PostMapping
    public ResponseEntity<Long> createGroup(@RequestBody GroupDTO groupDTO) {
        Long createdId = groupService.addGroup(groupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        GroupDTO groupDTO = groupService.getGroupById(id);
        return ResponseEntity.ok(groupDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroupById(id);
        return ResponseEntity.noContent().build();
    }
}