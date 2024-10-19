package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
