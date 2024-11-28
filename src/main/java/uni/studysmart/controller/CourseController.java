package uni.studysmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.studysmart.model.Course;
import uni.studysmart.request.CourseRequest;
import uni.studysmart.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/save")
    public ResponseEntity<String> saveCourse(@RequestBody CourseRequest courseRequest) {
        Course course = courseService.saveCourse(courseRequest);
        if(course == null) {
            return ResponseEntity.badRequest().body("Błąd podczas dodawania kursu!");
        }
        if(course.getLecturer() == null){
            return ResponseEntity.badRequest().body("Nie znaleziono takiego prowadzącego!");
        }
        if(course.getGroups() == null){
            return ResponseEntity.badRequest().body("Nie znaleziono takiej grupy!");
        }
        if(course.getCourseDuration() > 0){
            return ResponseEntity.badRequest().body("Nie dodałeś długości trwania kursu!");
        }
        return ResponseEntity.ok("Dodano pomyślnie kurs: " + course.getName());
    }
}
