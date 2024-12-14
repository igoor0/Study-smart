package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Availability;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.repository.AvailabilityRepository;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.utils.TimeRange;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final LecturerRepository lecturerRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, LecturerRepository lecturerRepository) {
        this.availabilityRepository = availabilityRepository;
        this.lecturerRepository = lecturerRepository;
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
    public List<Long> addAvailabilities(List<AvailabilityDTO> availabilityDTOList) {
        List<Long> availabilityIds = new ArrayList<>();
        List<Availability> availabilities = new ArrayList<>();

        for(AvailabilityDTO availabilityDTO : availabilityDTOList) {
            Availability availability = convertToEntity(availabilityDTO);
            availabilities.add(availability);
        }
        List<Availability> savedAvailabilities = availabilityRepository.saveAll(availabilities);

        for(Availability savedAvailability : savedAvailabilities) {
            availabilityIds.add(savedAvailability.getId());
        }
        return availabilityIds;
    }

    public AvailabilityDTO getAvailabilityById(Long id) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Availability not found"));
        return convertToDTO(availability);
    }

    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }

    private AvailabilityDTO convertToDTO(Availability availability) {
        return new AvailabilityDTO(
                availability.getId(),
                availability.getDayId(),
                availability.getDayName(),
                availability.getTimes(),
                availability.getTimeRanges() != null ? convertTimeRangesToString(availability.getTimeRanges()) : null,

                availability.getLecturer() != null ? availability.getLecturer().getId() : null
        );
    }

    private Availability convertToEntity(AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability();

        availability.setId(availabilityDTO.getId());
        availability.setDayId(availabilityDTO.getDayId());
        availability.setDayName(availabilityDTO.getDayName());
        availability.setTimes(availabilityDTO.getTimes());

        if (availabilityDTO.getLecturerId() != null) {
            Lecturer lecturer = lecturerRepository.findById(availabilityDTO.getLecturerId())
                    .orElseThrow(() -> new ApiRequestException("Lecturer not found"));
            availability.setLecturer(lecturer);
        }

        if (availabilityDTO.getTimeRanges() != null) {
            List<TimeRange> timeRanges = availabilityDTO.getTimeRanges().stream()
                    .map(timeRangeStrings -> new TimeRange(
                            LocalTime.parse(timeRangeStrings.get(0)),
                            LocalTime.parse(timeRangeStrings.get(1))
                    ))
                    .collect(Collectors.toList());
            availability.setTimeRanges(timeRanges);
        }

        return availability;
    }
    private List<List<String>> convertTimeRangesToString(List<TimeRange> timeRanges) {
        return timeRanges.stream()
                .map(timeRange -> List.of(timeRange.getStartTime().toString(), timeRange.getEndTime().toString()))
                .collect(Collectors.toList());
    }
}