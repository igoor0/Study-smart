package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Preference;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findByStudent_GroupIdAndCourseId(Long groupId, Long courseId);
}
