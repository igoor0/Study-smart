package uni.studysmart.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uni.studysmart.model.Group;
import uni.studysmart.model.Preference;

import java.time.Year;
import java.util.List;

@Getter
@Setter
@Entity(name = "students")
public class Student extends User {

    private String indexNumber;
    private String major;
    @ManyToOne
    private Group group;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Preference> preferences;

//    @PostPersist
//    private void generateIndexNumberAfterPersist() {
//        if (this.indexNumber == null) {
//            int currentYear = Year.now().getValue();
//            long shiftedId = this.getId() + currentYear + 500;
//            this.indexNumber = "STU" + shiftedId;
//        }
//    }
}
