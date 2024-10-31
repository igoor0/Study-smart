package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Availability;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByLecturerId(Long lecturerId);
}
