package uni.studysmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.CourseDTO;
import uni.studysmart.model.Course;
import uni.studysmart.request.CourseRequest;
import uni.studysmart.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/save")
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO courseRequest) {
        courseService.createCourse(courseRequest);
        return ResponseEntity.ok("Utworzono kurs pomyślnie");
    }
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}