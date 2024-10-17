package uni.studysmart;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long studentId;
    private String firstName;
    private String lastName;
    private String indexNumber;
    private String fieldOfStudy;
    private Long year;
    private String studentGroup;
    private LocalDateTime createdAt;
}
