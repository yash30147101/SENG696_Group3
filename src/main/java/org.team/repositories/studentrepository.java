pack org.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.models.student;

public interface studentRepository extends JpaRepository<student, String> {

    student save(student student);

    student findByUsername(String username);

    student findstudentByIdEquals(String id);

    student findstudentByEmailEquals(String email);

}
