package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
