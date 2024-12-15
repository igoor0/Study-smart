package uni.studysmart.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupDTO {

    private Long id;
    private String name;
    private List<Long> studentIdList;
    private List<Long> courseIdList;

    public GroupDTO(String name, List<Long> studentIdList, List<Long> courseIdList) {
        this.name = name;
        this.studentIdList = studentIdList;
        this.courseIdList = courseIdList;
    }

    public GroupDTO(String name, List<Long> studentIdList) {
        this.studentIdList = studentIdList;
        this.name = name;
    }

    public GroupDTO(String name) {
        this.name = name;
    }
}
