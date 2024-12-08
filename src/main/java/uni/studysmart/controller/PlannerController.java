package uni.studysmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.service.PlannerService;

@RestController
@RequestMapping("api/planners")
public class PlannerController {

    @Autowired
    private PlannerService plannerService;

    @PostMapping("/add/{studentId}/to/{groupId}")
    public ResponseEntity<String> addStudentToGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        return plannerService.addStudentToGroup(studentId, groupId);
    }

    @PutMapping("/removeStudentFromGroup")
    public ResponseEntity<String> deleteStudentFromGroup(@RequestParam Long studentId, @RequestParam Long groupId) {
        return plannerService.removeStudentFromGroup(studentId, groupId);
    }

    @PostMapping("/addLecturerToCourse")
    public ResponseEntity<String> addLecturerToCourse(@RequestParam Long lecturerId, @RequestParam Long courseId) {
        return plannerService.addLecturerToCourse(lecturerId, courseId);
    }

    @PutMapping("/removeLecturerFromCourse")
    public ResponseEntity<String> deleteLecturerFromCourse(@RequestParam Long courseId) {
        return plannerService.removeLecturerFromCourse(courseId);
    }

    @PostMapping("/addGroupToCourse")
    public ResponseEntity<String> addCourseToGroup(@RequestParam Long courseId, @RequestParam Long groupId) {
        return plannerService.addGroupToCourse(courseId, groupId);
    }

    @PutMapping("/removeGroupFromCourse")
    public ResponseEntity<String> deleteGroupFromCourse(@RequestParam Long courseId, @RequestParam Long groupId) {
        return plannerService.removeGroupFromCourse(courseId, groupId);
    }

}
