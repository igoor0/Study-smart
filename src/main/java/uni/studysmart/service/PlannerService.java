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



    //TODO CRUD DO SCHEDULE
    //TODO ADD - CALEGO CRUDA COURSES WYSYLA REQUEST TUTAJ TEN


}

