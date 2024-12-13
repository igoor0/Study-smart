package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.*;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.repository.AvailabilityRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.utils.TimeRange;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final LecturerRepository lecturerRepository;
    private final PreferenceRepository preferenceRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, LecturerRepository lecturerRepository, PreferenceRepository preferenceRepository) {
        this.availabilityRepository = availabilityRepository;
        this.lecturerRepository = lecturerRepository;
        this.preferenceRepository = preferenceRepository;
    }

    public List<AvailabilityDTO> getAllAvailabilities() {
        return availabilityRepository.findAll().stream().map(availability -> {
            AvailabilityDTO dto = new AvailabilityDTO();
            dto.setId(availability.getId());
            dto.setIden(availability.getDayOfWeek().getValue());
            dto.setDayName(availability.getDayOfWeek().name());
            dto.setTimeRanges(availability.getTimeRanges().stream()
                    .map(range -> List.of(range.getStartTime().toString(), range.getEndTime().toString()))
                    .collect(Collectors.toList()));
            dto.setLecturerId(availability.getLecturer().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public Long addAvailability(AvailabilityDTO dto) {
        Availability availability = new Availability();
        availability.setDayOfWeek(DayOfWeek.of(dto.getIden())); // Zamiana ID dnia na DayOfWeek
        availability.setLecturer(lecturerRepository.findById(dto.getLecturerId()).orElseThrow());
        availability.setTimeRanges(dto.getTimeRanges().stream()
                .map(range -> new TimeRange(LocalTime.parse(range.get(0)), LocalTime.parse(range.get(1))))
                .collect(Collectors.toList()));
        availabilityRepository.save(availability);
        return availability.getId();
    }
}

