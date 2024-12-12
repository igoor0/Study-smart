package uni.studysmart.auth;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class StudentRegisterRequest extends RegisterRequest {
    private String indexNumber;
    private String major;

}
