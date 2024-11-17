package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Availability;
import uni.studysmart.model.Lecturer;
import uni.studysmart.repository.AvailabilityRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.request.AvailabilityRequest;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private LecturerRepository lecturerRepository;
    private AvailabilityRequest availabilityRequest;

    public void addAvailability(AvailabilityRequest request) {
        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono wykładowcy o podanym ID"));

        // Parse String times into LocalTime
        LocalTime startTime;
        LocalTime endTime;
        try {
            startTime = LocalTime.parse(availabilityRequest.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
            endTime = LocalTime.parse(availabilityRequest.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
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

    public ResponseEntity<List<Availability>> getAllAvailabilities() {
        List<Availability> availabilities = availabilityRepository.findAll();
        return ResponseEntity.ok(availabilities);
    }

    public ResponseEntity<Availability> getAvailabilityById(Long id) {
        Optional<Availability> availability = availabilityRepository.findById(id);
        return availability.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

