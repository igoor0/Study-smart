package uni.studysmart.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDTO {
    private Long id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private LecturerDTO lecturer;
}