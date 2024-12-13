package uni.studysmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private Long id;
    private Long courseId;
    private Long groupId;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

}