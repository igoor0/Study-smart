package uni.studysmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String indexNumber;
    private String major;
    private Long groupId;
    private List<Long> preferenceIdList;
}
