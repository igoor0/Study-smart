package uni.studysmart.auth;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Getter
public class LecturerRegisterRequest extends RegisterRequest {
    private String department;
    private String title;
    private String classRoom;
    private String officeNumber;

    public LecturerRegisterRequest(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    public LecturerRegisterRequest(String firstName, String lastName, String email, String password, String department, String title, String classRoom, String officeNumber) {
        super(firstName, lastName, email, password);
        this.department = department;
        this.title = title;
        this.classRoom = classRoom;
        this.officeNumber = officeNumber;
    }

}
