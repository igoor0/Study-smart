package uni.studysmart.service;


import org.springframework.stereotype.Service;
import uni.studysmart.dto.*;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.*;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.ScheduleRepository;



import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final int SEMESTER_WEEKS = 15;

    private final CourseRepository courseRepository;

    private final GroupRepository groupRepository;

    private final LecturerRepository lecturerRepository;

    private final ScheduleRepository scheduleRepository;

    public CourseService(CourseRepository courseRepository, GroupRepository groupRepository, LecturerRepository lecturerRepository, ScheduleRepository scheduleRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
        this.lecturerRepository = lecturerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Long addCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        course = courseRepository.save(course);
        return course.getId();
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Course not found"));
        return convertToDTO(course);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    public int getWeeklyDuration(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiRequestException("Course not found"));
        if (course != null) {
            return course.getCourseDuration() / SEMESTER_WEEKS; //Założenie, że semestr trwa 15 tygodni
        }
        return 0;
    }

//    public boolean isCourseInSchedule(Long scheduleId) {
//        Schedule schedule = scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new ApiRequestException("Schedule not found"));
//        if (schedule == null) return false; //TODO: do przemyślenia
//        return schedule.getCourse().getIsScheduled();
//    }
//
//    //Czy możemy ten kurs wpisać w tych godzinach (czy występuje konflikt w innych grupach dla tego kursu)
//    public boolean hasScheduleConflict(Long courseId, LocalTime startTime, LocalTime endTime) {
//        Course course = courseRepository.findById(courseId).orElse(null);
//        if (course == null) return true;
//        List<Schedule> schedules = scheduleRepository.findAll();
//        for (Schedule schedule : schedules) {
//            for (Lecturer lecturer : lecturerRepository.findAllByCourses_Id(courseId))
//                if (schedule.getCourse().getName().equals(course.getName())) {
//                    //DWA PRZYPADKI
//                    //PIERWSZY - Kurs jest przed i w trakcie innych zajęć
//                    //DRUGI - Kurs jest po i w trakcie innych zajęć
//
//                    //Czy dana grupa ma w tych godzinach zajęcia
//                    if ((schedule.getCourse().getIsScheduled() && schedule.getCourse().getStartTime().isBefore(course.getEndTime())) ||
//                            (schedule.getCourse().getIsScheduled() && schedule.getCourse().getEndTime().isBefore(course.getStartTime()))) {
//                        //Ile grup ma w tych godzinach zajęcia / Jeżeli mniej niż jest dostępnych wykładowców tego przedmiotu, to możemy mieć tu zajęcia z dostępnym wykładowcą (ale w innej sali hehe)
//                        return schedule.getCourse().getLecturer().equals(lecturer);
//                    } else return true;
//                }
//        }
//        return false;
//    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Course not found with id: " + id));

        existingCourse.setName(courseDTO.getName());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setIsScheduled(courseDTO.isScheduled());
        existingCourse.setCourseDuration(courseDTO.getCourseDuration());

        if (courseDTO.getLecturerId() != null) {
            Lecturer lecturer = lecturerRepository.findById(courseDTO.getLecturerId())
                    .orElseThrow(() -> new ApiRequestException("Lecturer not found"));
            existingCourse.setLecturer(lecturer);
        }

        if (courseDTO.getGroupId() != null) {
            Group group = groupRepository.findById(courseDTO.getGroupId())
                    .orElseThrow(() -> new ApiRequestException("Group not found"));
            existingCourse.setGroup(group);
        }

        Course updatedCourse = courseRepository.save(existingCourse);
        return convertToDTO(updatedCourse);
    }

    private CourseDTO convertToDTO(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCourseDuration(),
                course.getLecturer() != null ? course.getLecturer().getId() : null,
                course.getGroup() != null ? course.getGroup().getId() : null,
                course.getIsScheduled()
        );
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();

        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setCourseDuration(courseDTO.getCourseDuration());
        course.setIsScheduled(courseDTO.isScheduled());

        if (courseDTO.getLecturerId() != null) {
            lecturerRepository.findById(courseDTO.getLecturerId()).ifPresentOrElse(
                    course::setLecturer,
                    () -> {
                        throw new ApiRequestException("Lecturer with ID " + courseDTO.getLecturerId() + " not found");
                    }
            );
        } else {
            course.setLecturer(null);
        }
        if (courseDTO.getGroupId() != null) {
            groupRepository.findById(courseDTO.getGroupId()).ifPresentOrElse(
                    course::setGroup,
                    () -> {
                        throw new ApiRequestException("Lecturer with ID " + courseDTO.getGroupId() + " not found");
                    }
            );
        } else {
            course.setGroup(null);
        }


        return course;
    }
}