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
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private LecturerRepository lecturerRepository;

    public void addAvailability(AvailabilityRequest request) {
        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono wykładowcy o podanym ID"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(request.getStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(request.getEndTime(), formatter);

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

