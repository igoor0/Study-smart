package uni.studysmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.StudentDTO;
import uni.studysmart.model.user.Student;
import uni.studysmart.service.PlannerService;
import uni.studysmart.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final PlannerService plannerService;

    public StudentController(StudentService studentService, PlannerService plannerService) {
        this.studentService = studentService;
        this.plannerService = plannerService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{studentId}/add-to-group/{groupId}")
    public ResponseEntity<Long> addStudentToGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
            Long updatedStudentId = studentService.addStudentToGroup(studentId, groupId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedStudentId);
    }

    @PutMapping("/{studentId}/remove-from-group/{groupId}")
    public ResponseEntity<String> deleteStudentFromGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
            Long updatedStudentId = studentService.removeStudentFromGroup(studentId, groupId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedStudentId.toString());
    }
}
