package uni.studysmart.auth;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class PlannerRegisterRequest extends RegisterRequest {
    private String description;
    private String officeNumber;

    public PlannerRegisterRequest(String firstName, String lastName, String email, String password, String description, String officeNumber) {
        super(firstName, lastName, email, password);
        this.description = description;
        this.officeNumber = officeNumber;
    }
    public PlannerRegisterRequest(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }
}
