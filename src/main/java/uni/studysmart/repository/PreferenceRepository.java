package uni.studysmart.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Preference;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findByStudent_GroupIdAndCourseId(Long groupId, Long courseId);

}
