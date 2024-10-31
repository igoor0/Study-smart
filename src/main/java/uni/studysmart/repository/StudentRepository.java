package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIndexNumber(String indexNumber);
    Optional<List<Student>> findAllByMajor(String major);
}
