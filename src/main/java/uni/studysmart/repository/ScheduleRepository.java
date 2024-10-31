package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
