package uni.studysmart.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceDTO {
    private Long studentId;
    private Long courseId;
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
}