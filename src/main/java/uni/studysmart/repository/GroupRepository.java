package uni.studysmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.studysmart.model.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findById(Long groupId);
}
