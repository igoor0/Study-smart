package uni.studysmart.auth;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class LecturerRegisterRequest extends RegisterRequest {
    private String department;
    private String title;
    private String classRoom;
    private String officeNumber;

    public LecturerRegisterRequest(String anna, String nowak, String mail, String password, String informatics, String s, String a1, String number) {
    }
}
