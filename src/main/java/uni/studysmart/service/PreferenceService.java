package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Availability;
import uni.studysmart.model.Preference;
import uni.studysmart.model.user.Student;
import uni.studysmart.model.Course;
import uni.studysmart.repository.AvailabilityRepository;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.repository.StudentRepository;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.dto.PreferenceDTO;
import uni.studysmart.model.TimeRange;
import uni.studysmart.utils.PreferenceValidator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uni.studysmart.utils.TimeRangeParser.convertTimeRangesToString;
import static uni.studysmart.utils.TimeRangeParser.parseTimeRanges;

@Service
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityService availabilityService;
    private final PreferenceValidator preferenceValidator;

    public PreferenceService(PreferenceRepository preferenceRepository, StudentRepository studentRepository, CourseRepository courseRepository, AvailabilityRepository availabilityRepository, AvailabilityService availabilityService, PreferenceValidator preferenceValidator) {
        this.preferenceRepository = preferenceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.availabilityRepository = availabilityRepository;
        this.availabilityService = availabilityService;
        this.preferenceValidator = preferenceValidator;
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
    public List<Long> addPreferences(List<PreferenceDTO> preferenceDTOList) {
        List<Long> preferenceIdList = new ArrayList<>();
        List<Preference> preferences = new ArrayList<>();

        for (PreferenceDTO preferenceDTO : preferenceDTOList) {
            Availability availability = availabilityRepository
                    .findByDayId(preferenceDTO.getDayId())
                    .orElseThrow(() -> new ApiRequestException("No availability found for the specified day (dayId: " + preferenceDTO.getDayId() + ")"));

            AvailabilityDTO availabilityDTO = availabilityService.convertToDTO(availability);
            boolean isValid = preferenceValidator.validatePreferenceWithAvailability(preferenceDTO, availabilityDTO);
            if (!isValid) {
                throw new ApiRequestException("Preference time ranges do not match the availability for the given day (dayId: " + preferenceDTO.getDayId() + ")");
            }

            // Konwersja i dodanie do listy
            Preference preference = convertToEntity(preferenceDTO);
            preferences.add(preference);
        }

        // Zapis wszystkich preferencji w bazie danych
        List<Preference> savedPreferences = preferenceRepository.saveAll(preferences);

        // Dodanie zapisanych preferencji do listy zwracanych ID
        for (Preference preference : savedPreferences) {
            preferenceIdList.add(preference.getId());
        }

        return preferenceIdList;
    }


//    public List<Long> addPreferences(List<PreferenceDTO> preferenceDTOList) {
//        List<Long> preferenceIdList = new ArrayList<>();
//        List<Preference> preferences = new ArrayList<>();
//
//        for (PreferenceDTO preferenceDTO : preferenceDTOList) {
//            Preference preference = convertToEntity(preferenceDTO);
//            preferences.add(preference);
//        }
//
//        List<Preference> savedPreferences = preferenceRepository.saveAll(preferences);
//        for(Preference preference : savedPreferences) {
//            preferenceIdList.add(preference.getId());
//        }
//        return preferenceIdList;
//    }


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
                preference.getTimeRanges() != null ? convertTimeRangesToString(preference.getTimeRanges()) : null,
                preference.getTimes(),
                preference.getCourse() != null ? preference.getCourse().getId() : null,
                preference.getStudent() != null ? preference.getStudent().getId() : null
        );
    }

    private Preference convertToEntity(PreferenceDTO preferenceDTO) {
        Preference preference = new Preference();
        preference.setId(preferenceDTO.getId());
        preference.setDayId(preferenceDTO.getDayId());
        preference.setDayName(preferenceDTO.getDayName());
        preference.setTimes(preferenceDTO.getTimes());

        List<TimeRange> timeRanges = parseTimeRanges(preferenceDTO.getTimeRanges());
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


}