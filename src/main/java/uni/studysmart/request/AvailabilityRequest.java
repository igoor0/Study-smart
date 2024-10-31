package uni.studysmart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRequest {
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
    private Long lecturerId;
}
