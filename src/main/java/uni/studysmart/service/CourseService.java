package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.dto.CourseDTO;
import uni.studysmart.dto.GroupDTO;
import uni.studysmart.dto.LecturerDTO;
import uni.studysmart.model.*;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.ScheduleRepository;
import uni.studysmart.request.CourseRequest;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final int SEMESTER_WEEKS = 15;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public int getWeeklyDuration(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return -1;
        return course.getCourseDuration() / SEMESTER_WEEKS; //Założenie, że semestr trwa 15 tygodni
    }

    public boolean isCourseInSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
        if (schedule == null) return false; //TODO: do przemyślenia
        return schedule.getCourse().isScheduled();
    }

    //Czy możemy ten kurs wpisać w tych godzinach (czy występuje konflikt w innych grupach dla tego kursu)
    public boolean hasScheduleConflict(Long courseId, LocalTime startTime, LocalTime endTime) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return true;
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            for (Lecturer lecturer : lecturerRepository.findAllByCourses_Id(courseId))
                if (schedule.getCourse().getName().equals(course.getName())) {
                    //DWA PRZYPADKI
                    //PIERWSZY - Kurs jest przed i w trakcie innych zajęć
                    //DRUGI - Kurs jest po i w trakcie innych zajęć

                    //Czy dana grupa ma w tych godzinach zajęcia
                    if ((schedule.getCourse().isScheduled() && schedule.getCourse().getStartTime().isBefore(course.getEndTime())) ||
                            (schedule.getCourse().isScheduled() && schedule.getCourse().getEndTime().isBefore(course.getStartTime()))) {
                        //Ile grup ma w tych godzinach zajęcia / Jeżeli mniej niż jest dostępnych wykładowców tego przedmiotu, to możemy mieć tu zajęcia z dostępnym wykładowcą (ale w innej sali hehe)
                        return schedule.getCourse().getLecturer().equals(lecturer);
                    } else return true;
                }
        }
        return false;
    }


    public CourseDTO createCourse(CourseDTO courseDTO) {
        // Mapowanie DTO na encję
        Course course = courseMapper.toCourse(courseDTO);

        // Walidacje
        if (course.getLecturer() == null) {
            throw new IllegalArgumentException("Nie znaleziono takiego prowadzącego!");
        }
        if (course.getGroups() == null || course.getGroups().isEmpty()) {
            throw new IllegalArgumentException("Nie znaleziono takiej grupy!");
        }
        if (course.getCourseDuration() <= 0) {
            throw new IllegalArgumentException("Nie dodałeś długości trwania kursu!");
        }

        // Zapisanie do bazy
        Course savedCourse = courseRepository.save(course);

        // Zwrócenie DTO
        return courseMapper.toCourseDTO(savedCourse);

    }

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toCourseDTOList(courses);
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
        return courseMapper.toCourseDTO(course);
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));

        existingCourse.setName(courseDTO.getName());
        existingCourse.setScheduled(courseDTO.isScheduled());
        existingCourse.setCourseDuration(courseDTO.getCourseDuration());

        // Konwersja String -> LocalTime
        existingCourse.setStartTime(courseDTO.getStartTime() != null
                ? LocalTime.parse(courseDTO.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"))
                : null);
        existingCourse.setEndTime(courseDTO.getEndTime() != null
                ? LocalTime.parse(courseDTO.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"))
                : null);

        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toCourseDTO(updatedCourse);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}


@Component
class CourseMapper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public CourseDTO toCourseDTO(Course course) {
        Lecturer lecturer = course.getLecturer();
        LecturerDTO lecturerDTO = new LecturerDTO(
                lecturer.getId(),
                lecturer.getFirstName(),
                lecturer.getLastName(),
                lecturer.getEmail()
        );

        List<GroupDTO> groupDTOList = course.getGroups().stream()
                .map(group -> new GroupDTO(group.getId(), group.getName()))
                .collect(Collectors.toList());

        String startTime = course.getStartTime() != null ? course.getStartTime().format(TIME_FORMATTER) : null;
        String endTime = course.getEndTime() != null ? course.getEndTime().format(TIME_FORMATTER) : null;

        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.isScheduled(),
                course.getCourseDuration(),
                startTime,
                endTime,
                groupDTOList,
                lecturerDTO
        );
    }

    public List<CourseDTO> toCourseDTOList(List<Course> courses) {
        return courses.stream()
                .map(this::toCourseDTO)
                .collect(Collectors.toList());
    }

    public Course toCourse(CourseDTO courseDTO) {
        LocalTime startTime = courseDTO.getStartTime() != null
                ? LocalTime.parse(courseDTO.getStartTime(), TIME_FORMATTER)
                : null;

        LocalTime endTime = courseDTO.getEndTime() != null
                ? LocalTime.parse(courseDTO.getEndTime(), TIME_FORMATTER)
                : null;

        // Tutaj ustaw resztę pól, np. poprzez wywołania repozytoriów, aby odnaleźć grupy i wykładowcę
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        course.setScheduled(courseDTO.isScheduled());
        course.setCourseDuration(courseDTO.getCourseDuration());
        course.setStartTime(startTime);
        course.setEndTime(endTime);

        // Ustaw grupy i wykładowcę, jeśli to wymagane

        return course;
    }
}