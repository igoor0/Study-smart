package uni.studysmart.auth;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@NoArgsConstructor
public class StudentRegisterRequest extends RegisterRequest {
    private String indexNumber;
    private String major;

    public StudentRegisterRequest(String firstName, String lastName, String mail, String password, String major) {
        super(firstName, lastName, mail, password);
        this.major = major;
    }
    public StudentRegisterRequest(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

}
