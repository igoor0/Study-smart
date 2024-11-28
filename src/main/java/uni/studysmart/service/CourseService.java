package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Course;
import uni.studysmart.model.Group;
import uni.studysmart.model.Lecturer;
import uni.studysmart.model.Schedule;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.repository.GroupRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.ScheduleRepository;
import uni.studysmart.request.CourseRequest;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    public int getWeeklyDuration(Long courseId){
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return -1;
        return course.getCourseDuration() / SEMESTER_WEEKS; //Założenie, że semestr trwa 15 tygodni
    }

    public boolean isCourseInSchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
        if(schedule == null) return false; //TODO: do przemyślenia
        return schedule.getCourse().isScheduled();
    }

    //Czy możemy ten kurs wpisać w tych godzinach (czy występuje konflikt w innych grupach dla tego kursu)
    public boolean hasScheduleConflict(Long courseId, LocalTime startTime, LocalTime endTime){
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return true;
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules){
            for (Lecturer lecturer : lecturerRepository.findAllByCourses_Id(courseId))
            if(schedule.getCourse().getName().equals(course.getName())){
                //DWA PRZYPADKI
                //PIERWSZY - Kurs jest przed i w trakcie innych zajęć
                //DRUGI - Kurs jest po i w trakcie innych zajęć

                //Czy dana grupa ma w tych godzinach zajęcia
                if( ( schedule.getCourse().isScheduled() && schedule.getCourse().getStartTime().isBefore(course.getEndTime()) ) ||
                        ( schedule.getCourse().isScheduled() && schedule.getCourse().getEndTime().isBefore(course.getStartTime()) )){
                    //Ile grup ma w tych godzinach zajęcia / Jeżeli mniej niż jest dostępnych wykładowców tego przedmiotu, to możemy mieć tu zajęcia z dostępnym wykładowcą (ale w innej sali hehe)
                    return schedule.getCourse().getLecturer().equals(lecturer);
                }
                else return true;
            }
        }
        return false;
    }


    public Course saveCourse(CourseRequest courseRequest) {
        List<Group> groupList = groupRepository.findGroupsByCourses_Id((long) Integer.parseInt(courseRequest.getGroupId()));
        Lecturer lecturer = lecturerRepository.findById((long) Integer.parseInt(courseRequest.getLecturerId())).orElse(null);
        Course course = new Course();
        course.setCourseDuration(courseRequest.getCourseDuration());
        course.setScheduled(false);
        course.setStartTime(null);
        course.setEndTime(null);
        course.setName(courseRequest.getName());
        course.setGroups(groupList);
        course.setLecturer(lecturer);
        return courseRepository.save(course);
    }


}
