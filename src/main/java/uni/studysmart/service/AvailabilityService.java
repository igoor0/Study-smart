package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.model.*;
import uni.studysmart.repository.AvailabilityRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.utils.Utils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final LecturerRepository lecturerRepository;
    private final PreferenceRepository preferenceRepository;
    private final Utils utils;

    public AvailabilityService(AvailabilityRepository availabilityRepository, LecturerRepository lecturerRepository, PreferenceRepository preferenceRepository, Utils utils) {
        this.availabilityRepository = availabilityRepository;
        this.lecturerRepository = lecturerRepository;
        this.preferenceRepository = preferenceRepository;
        this.utils = utils;
    }


    public List<AvailabilityDTO> getAllAvailabilities() {
        return availabilityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public Long addAvailability(AvailabilityDTO availabilityDTO) {
        Availability availability = convertToEntity(availabilityDTO);
        availability = availabilityRepository.save(availability);
        return availability.getId();
    }

    public AvailabilityDTO getAvailabilityById(Long id) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found"));
        return convertToDTO(availability);
    }

    public void deleteAvailability(Long id) {
        deleteAllPreferencesBetweeenAvailability(id);
        availabilityRepository.deleteById(id);
    }

    private void deleteAllPreferencesBetweeenAvailability(Long availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Availability not found - cannot delete preferences at that period"));
        DayOfWeek dayOfWeek = availability.getDayOfWeek();
        LocalTime startTime = availability.getStartTime();
        LocalTime endTime = availability.getEndTime();

        preferenceRepository.deletePreferencesBetweenAvailability(dayOfWeek, startTime, endTime);
    }

    private AvailabilityDTO convertToDTO(Availability availability) {
        return new AvailabilityDTO(
                availability.getId(),
                availability.getDayOfWeek() != null ? availability.getDayOfWeek().toString() : null,
                availability.getStartTime() != null ? availability.getStartTime().toString() : null,
                availability.getEndTime() != null ? availability.getEndTime().toString() : null,
                availability.getLecturer() != null ? availability.getLecturer().getId() : null
        );
    }

    private Availability convertToEntity(AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability();

        availability.setId(availabilityDTO.getId());
        if (availabilityDTO.getDayOfWeek() != null) {
            availability.setDayOfWeek(DayOfWeek.valueOf(availabilityDTO.getDayOfWeek().toUpperCase()));
        }
        if (availabilityDTO.getStartTime() != null) {
            availability.setStartTime(LocalTime.parse(availabilityDTO.getStartTime()));
        }
        if (availabilityDTO.getEndTime() != null) {
            availability.setEndTime(LocalTime.parse(availabilityDTO.getEndTime()));
        }

        if (availabilityDTO.getLecturerId() != null) {
            Lecturer lecturer = lecturerRepository.findById(availabilityDTO.getLecturerId())
                    .orElseThrow(() -> new RuntimeException("Lecturer not found"));
            availability.setLecturer(lecturer);
        }

        return availability;
    }
}

