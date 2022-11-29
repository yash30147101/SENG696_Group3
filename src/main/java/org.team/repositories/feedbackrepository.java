pack org.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team.models.FacultyNotes;

import java.util.List;

@Repository
public interface FacultyNotesRepository extends JpaRepository<FacultyNotes, Long> {
    List<FacultyNotes> findByfaculty_EmailOrderByIdDesc(String email);
}
