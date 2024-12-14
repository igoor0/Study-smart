package uni.studysmart.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.studysmart.dto.LecturerDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Course;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.repository.LecturerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;

    public LecturerService(LecturerRepository lecturerRepository, CourseRepository courseRepository) {
        this.lecturerRepository = lecturerRepository;
        this.courseRepository = courseRepository;
    }

    public Lecturer saveLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    public Long enableLecturer(Long lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ApiRequestException("Lecturer not found"));
        lecturer.setConfirmed(true);
        lecturerRepository.save(lecturer);
        return lecturer.getId();
    }

    public List<LecturerDTO> getAllLecturers() {
        return lecturerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Long addCourseToLecturer(Long lecturerId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiRequestException("Course not found"));
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ApiRequestException("Lecturer not found"));

        course.setLecturer(lecturer);
        lecturer.getCourses().add(course);
        lecturerRepository.save(lecturer);
        courseRepository.save(course);
        return lecturer.getId();
    }

    public Long removeCourseFromLecturer(Long lecturerId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiRequestException("Course not found"));
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ApiRequestException("Lecturer not found"));
        lecturer.getCourses().remove(course);
        course.setLecturer(null);
        lecturerRepository.save(lecturer);
        courseRepository.save(course);
        return lecturer.getId();
    }

    public Long confirmLecturer(Long lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ApiRequestException("Lecturer not found"));
        lecturer.setConfirmed(true);
        lecturerRepository.save(lecturer);
        return lecturer.getId();
    }

    public void confirmAllLecturers() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        for (Lecturer lecturer : lecturers) {
            lecturer.setConfirmed(true);
            lecturerRepository.save(lecturer);
        }
    }

    private LecturerDTO convertToDTO(Lecturer lecturer) {
        return new LecturerDTO(
                lecturer.getId(),
                lecturer.getFirstName(),
                lecturer.getLastName(),
                lecturer.getEmail(),
                lecturer.getDepartment(),
                lecturer.getTitle(),
                lecturer.getClassRoom(),
                lecturer.getOfficeNumber(),
                lecturer.isEnabled()
        );
    }
}
