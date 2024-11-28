package uni.studysmart.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupDTO {
    private Long id;
    private String groupName;

    public GroupDTO(Long id, String name) {
    }
}
