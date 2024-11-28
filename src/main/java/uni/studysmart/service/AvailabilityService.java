package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.dto.LecturerDTO;
import uni.studysmart.model.Availability;
import uni.studysmart.model.Lecturer;
import uni.studysmart.repository.AvailabilityRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.request.AvailabilityRequest;
import uni.studysmart.service.utils.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private Utils utils;

    public void addAvailability(AvailabilityRequest request) {
        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono wykładowcy o podanym ID"));

        // Parse String times into LocalTime
        LocalTime startTime;
        LocalTime endTime;
        try {
            startTime = LocalTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
            endTime = LocalTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Use hh:mm, e.g., 09:00", e);
        }

        Availability availability = new Availability();
        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);
        availability.setLecturer(lecturer);
        availabilityRepository.save(availability);
    }

    public ResponseEntity<List<AvailabilityDTO>> getAllAvailabilities() {
        List<Availability> availabilities = availabilityRepository.findAll();
        List<AvailabilityDTO> availabilityDTOs = availabilities.stream().map(availability -> {
            Lecturer lecturer = availability.getLecturer();
            LecturerDTO lecturerDTO = new LecturerDTO(
                    lecturer.getId(),
                    lecturer.getFirstName(),
                    lecturer.getLastName(),
                    lecturer.getEmail()
            );
            return new AvailabilityDTO(
                    availability.getId(),
                    availability.getDayOfWeek().name(),
                    availability.getStartTime().toString(),
                    availability.getEndTime().toString(),
                    lecturerDTO
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(availabilityDTOs);
    }

    public ResponseEntity<Availability> getAvailabilityById(Long id) {
        Optional<Availability> availability = availabilityRepository.findById(id);
        return ResponseEntity.ok(availability.orElseThrow(() -> new IllegalArgumentException("Availibility not found")));
    }


    public ResponseEntity deleteAvailability(Long id) {
        Optional<Availability> availability = availabilityRepository.findById(id);

        if (availability.isPresent()) {
            utils.deleteAllPreferencesConflictsAvailability(availability.orElseThrow(() -> new IllegalArgumentException("Availability not found")));
            availabilityRepository.delete(availability.get());
        }
        return ResponseEntity.ok(availability.orElseThrow(() -> new IllegalArgumentException("Availability not found")));
    }
}

