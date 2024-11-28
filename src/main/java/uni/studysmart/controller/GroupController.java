package uni.studysmart.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.model.Group;
import uni.studysmart.request.GroupRequest;
import uni.studysmart.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping
    public ResponseEntity<String> addGroup(@RequestBody GroupRequest group) {
        groupService.addGroup(group);
        return ResponseEntity.ok("Grupa dodana pomyślnie");
    }
}