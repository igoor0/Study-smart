package uni.studysmart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Data
public class AvailabilityDTO {
    private Long id;
    private Long dayId;
    private String dayName;
    private List<String> times;
    private List<List<String>> timeRanges;
    private Long lecturerId;

    public AvailabilityDTO(Long id, Long dayId, String dayName, List<String> times, List<List<String>> timeRanges, Long lecturerId) {
        this.id = id;
        this.dayId = dayId;
        this.dayName = dayName;
        this.times = times;
        this.timeRanges = timeRanges;
        this.lecturerId = lecturerId;
    }

    public AvailabilityDTO(Long dayId, String dayName, List<String> times, List<List<String>> timeRanges, Long lecturerId) {
        this.dayId = dayId;
        this.dayName = dayName;
        this.times = times;
        this.timeRanges = timeRanges;
        this.lecturerId = lecturerId;
    }
}