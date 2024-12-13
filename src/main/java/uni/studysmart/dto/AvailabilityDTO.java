package uni.studysmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data

public class AvailabilityDTO {
    private Long id;
    private Long dayId;
    private String dayName;
    private List<String> times;
    private List<List<String>> timeRanges;
    private Long lecturerId;

    public AvailabilityDTO(Long id, Long dayId, String dayName, List<List<String>> lists, Long aLong) {
        this.id = id;
        this.dayId = dayId;
        this.dayName = dayName;
        this.times = new ArrayList<>();
        this.timeRanges = new ArrayList<>();
        this.lecturerId = aLong;

    }
}