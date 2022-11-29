pack org.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.models.department;


public interface departmentRepository extends JpaRepository<department, Long>{

    department findByName(String name);

    department deleteByName(String name);
}