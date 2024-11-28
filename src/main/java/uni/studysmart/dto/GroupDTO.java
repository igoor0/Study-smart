package uni.studysmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String groupName;

    public GroupDTO(Long id, String name) {
    }
}
