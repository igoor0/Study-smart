package uni.studysmart.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDTO {
    private Long id;
    private Integer iden;
    private String startTime;
    private String endTime;
    private Long studentId;
    private Long courseId;

    public PreferenceDTO(Integer iden, String startTime, String endTime, long studentId, long courseId) {
        this.iden = iden;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.courseId = courseId;
    }
}