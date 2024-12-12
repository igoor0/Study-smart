package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Preference;

import uni.studysmart.model.user.Student;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.repository.StudentRepository;
import uni.studysmart.model.Course;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.dto.PreferenceDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public PreferenceService(PreferenceRepository preferenceRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.preferenceRepository = preferenceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<PreferenceDTO> getAllPreferences() {
        return preferenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Long addPreference(PreferenceDTO preferenceDTO) {
        Preference preference = convertToEntity(preferenceDTO);
        preference = preferenceRepository.save(preference);
        return preference.getId();
    }

    public PreferenceDTO getPreferenceById(Long id) {
        Preference preference = preferenceRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Preference not found"));
        return convertToDTO(preference);
    }

    public void deletePreferenceById(Long id) {
        preferenceRepository.deleteById(id);
    }

    private PreferenceDTO convertToDTO(Preference preference) {
        return new PreferenceDTO(
                preference.getId(),
                preference.getDayOfWeek() != null ? preference.getDayOfWeek().toString() : null,
                preference.getStartTime() != null ? preference.getStartTime().toString() : null,
                preference.getEndTime() != null ? preference.getEndTime().toString() : null,
                preference.getStudent() != null ? preference.getStudent().getId() : null,
                preference.getCourse() != null ? preference.getCourse().getId() : null
        );
    }

    private Preference convertToEntity(PreferenceDTO preferenceDTO) {
        Preference preference = new Preference();

        preference.setId(preferenceDTO.getId());
        if (preferenceDTO.getDayOfWeek() != null) {
            preference.setDayOfWeek(DayOfWeek.valueOf(preferenceDTO.getDayOfWeek().toUpperCase()));
        }
        if (preferenceDTO.getStartTime() != null) {
            preference.setStartTime(LocalTime.parse(preferenceDTO.getStartTime()));
        }
        if (preferenceDTO.getEndTime() != null) {
            preference.setEndTime(LocalTime.parse(preferenceDTO.getEndTime()));
        }

        if (preferenceDTO.getStudentId() != null) {
            Student student = studentRepository.findById(preferenceDTO.getStudentId())
                    .orElseThrow(() -> new ApiRequestException("Student not found"));
            preference.setStudent(student);
        }

        if (preferenceDTO.getCourseId() != null) {
            Course course = courseRepository.findById(preferenceDTO.getCourseId())
                    .orElseThrow(() -> new ApiRequestException("Course not found"));
            preference.setCourse(course);
        }

        return preference;
    }
}


