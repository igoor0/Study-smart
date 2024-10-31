package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}
