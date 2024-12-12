package uni.studysmart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.LecturerDTO;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.service.LecturerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService, LecturerRepository lecturerRepository) {
        this.lecturerService = lecturerService;
    }

    @GetMapping
    public ResponseEntity<List<Lecturer>> getAllLecturers() {
        List<Lecturer> lecturerList = lecturerService.getAllLecturers();
        return ResponseEntity.ok(lecturerList);
    }

    @PutMapping("/{lecturerId}/add-course/{courseId}")
    public ResponseEntity<Long> addCourseToLecturer(@PathVariable Long lecturerId, @PathVariable Long courseId) {
        Long updatedLecturerId = lecturerService.addCourseToLecturer(lecturerId, courseId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedLecturerId);
    }

    @PutMapping("/{lecturerId}/remove-course/{courseId}")
    public ResponseEntity<Long> removeCourseFromLecturer(@PathVariable Long lecturerId, @PathVariable Long courseId) {
        Long updatedLecturerId = lecturerService.removeCourseFromLecturer(lecturerId, courseId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedLecturerId);
    }

}
