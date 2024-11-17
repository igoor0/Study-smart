package uni.studysmart.request;

import lombok.*;

import java.time.DayOfWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRequest {
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
    private Long lecturerId;
}
