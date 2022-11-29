pack org.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.models.faculty;

import java.util.List;
import java.util.Optional;

public interface facultyRepository extends JpaRepository<faculty, String> {

    faculty findByUsername(String username);

    Optional<faculty> findById(String id);

    List<faculty> findfacultysBydepartmentNameEquals(String specName);

    faculty findByFirstNameAndEmail(String firstName, String email);
}
