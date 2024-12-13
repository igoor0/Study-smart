package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Preference;
import uni.studysmart.model.user.Student;
import uni.studysmart.model.Course;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.repository.StudentRepository;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.dto.PreferenceDTO;
import uni.studysmart.utils.TimeRange;

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
                preference.getDayId(),
                preference.getDayName(),
                preference.getTimes(),
                preference.getTimeRanges() != null ? convertTimeRangesToString(preference.getTimeRanges()) : null,
                preference.getStudent() != null ? preference.getStudent().getId() : null,
                preference.getCourse() != null ? preference.getCourse().getId() : null
        );
    }

    private Preference convertToEntity(PreferenceDTO preferenceDTO) {
        Preference preference = new Preference();
        preference.setId(preferenceDTO.getId());
        preference.setDayId(preferenceDTO.getDayId());
        preference.setDayName(preferenceDTO.getDayName());
        preference.setTimes(preferenceDTO.getTimes());

        List<TimeRange> timeRanges = preferenceDTO.getTimeRanges().stream()
                .map(this::convertToTimeRange)
                .collect(Collectors.toList());

        preference.setTimeRanges(timeRanges);

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

    private TimeRange convertToTimeRange(List<String> timeRange) {
        if (timeRange.size() == 2) {
            LocalTime startTime = LocalTime.parse(timeRange.get(0));
            LocalTime endTime = LocalTime.parse(timeRange.get(1));
            return new TimeRange(startTime, endTime);
        }
        throw new ApiRequestException("Invalid time range format");
    }

    private List<List<String>> convertTimeRangesToString(List<TimeRange> timeRanges) {
        return timeRanges.stream()
                .map(timeRange -> List.of(timeRange.getStartTime().toString(), timeRange.getEndTime().toString()))
                .collect(Collectors.toList());
    }
}