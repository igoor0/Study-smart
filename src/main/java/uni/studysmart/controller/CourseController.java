package uni.studysmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.CourseDTO;
import uni.studysmart.model.Course;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.request.CourseRequest;
import uni.studysmart.service.CourseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/save")
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO courseRequest) {
        courseService.createCourse(courseRequest);
        return ResponseEntity.ok("Utworzono kurs pomyślnie");
    }
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping
    public ResponseEntity<CourseDTO> getCourseById(@RequestParam Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }
}