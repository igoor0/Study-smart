package uni.studysmart.model.user;

import jakarta.persistence.*;
import lombok.*;
import uni.studysmart.model.Availability;
import uni.studysmart.model.Course;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "lecturers")
public class Lecturer extends User {

    private String department;
    private String title;
    private String classRoom;
    private String officeNumber;
    private boolean confirmed;

    @OneToMany(mappedBy = "lecturer")
    private List<Course> courses;
    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private List<Availability> availabilities;

    @Override
    public boolean isEnabled() {
        return confirmed;
    }
}
