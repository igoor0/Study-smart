package uni.studysmart.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Preference;

import org.springframework.beans.factory.annotation.Autowired;
import uni.studysmart.model.Student;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.repository.StudentRepository;
import uni.studysmart.model.Course;
import uni.studysmart.repository.CourseRepository;
import uni.studysmart.request.PreferenceDTO;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void addPreference(PreferenceDTO preferenceRequest) {
        Student student = studentRepository.findById(preferenceRequest.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Course course = courseRepository.findById(preferenceRequest.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Parse String times into LocalTime
        LocalTime startTime;
        LocalTime endTime;

        try {
            startTime = LocalTime.parse(preferenceRequest.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
            endTime = LocalTime.parse(preferenceRequest.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Use hh:mm, e.g., 09:00", e);
        }
        Preference preference = new Preference();
        preference.setStudent(student);
        preference.setCourse(course);
        preference.setDayOfWeek(preferenceRequest.getDayOfWeek());
        preference.setStartTime(startTime);
        preference.setEndTime(endTime);

        preferenceRepository.save(preference);
    }
    public ResponseEntity<List<Preference>> getAllPreferences() {
        List<Preference> preferences = preferenceRepository.findAll();
        return ResponseEntity.ok(preferences);
    }

    public ResponseEntity<Preference> getPreferenceById(Long id) {
        Optional<Preference> preference = preferenceRepository.findById(id);
        return ResponseEntity.ok(preference.orElseThrow(() -> new IllegalArgumentException("Preference not found")));
    }

    public ResponseEntity<Preference> deletePreferenceById(Long id) {
        Optional<Preference> preference = preferenceRepository.findById(id);
        if (preference.isPresent()) {
            preferenceRepository.deleteById(id);
        }
        return ResponseEntity.ok(preference.orElseThrow(() -> new IllegalArgumentException("Preference not found")));
    }
}


