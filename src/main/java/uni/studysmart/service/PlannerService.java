package uni.studysmart.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Course;
import uni.studysmart.model.Group;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.model.user.Student;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.StudentRepository;

@Service
public class PlannerService {

    private final GroupService groupService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final GroupRepository groupRepository;

    public PlannerService(GroupService groupService, StudentRepository studentRepository, CourseRepository courseRepository, LecturerRepository lecturerRepository, GroupRepository groupRepository) {
        this.groupService = groupService;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.groupRepository = groupRepository;
    }

    public ResponseEntity<String> addStudentToGroup(Long studentId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        group.getStudents().add(student);
        student.setGroup(group);
        groupRepository.save(group);
        studentRepository.save(student);
        return ResponseEntity.ok("Student został dodany do grupy");
    }

    public ResponseEntity<String> removeStudentFromGroup(Long studentId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        group.getStudents().remove(student);
        student.setGroup(null);
        groupRepository.save(group);
        studentRepository.save(student);
        return ResponseEntity.ok("Student został usunięty z grupy");
    }



    public ResponseEntity<String> addLecturerToCourse(Long courseId, Long lecturerId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElse(null);
        if (lecturer == null) {
            return ResponseEntity.notFound().build();
        }
        course.setLecturer(lecturer);
        lecturer.getCourses().add(course);
        lecturerRepository.save(lecturer);
        courseRepository.save(course);
        return ResponseEntity.ok("Wykładowca został dodany do kursu");
    }

    public ResponseEntity<String> removeLecturerFromCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        course.setLecturer(null);
        courseRepository.save(course);
        return ResponseEntity.ok("Wykładowca został usunięty z kursu");
    }


    //TODO CRUD DO SCHEDULE
    //TODO ADD - CALEGO CRUDA COURSES WYSYLA REQUEST TUTAJ TEN


}

