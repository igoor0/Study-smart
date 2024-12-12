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

    public GroupDTO(String name, List<Long> studentIdList) {
        this.studentIdList = studentIdList;
        this.name = name;
    }

    public GroupDTO(String name) {
        this.name = name;
    }
}
