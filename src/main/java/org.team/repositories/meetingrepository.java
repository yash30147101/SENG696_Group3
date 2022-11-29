pack org.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.models.MeetingData;

public interface MeetingRepository extends JpaRepository<MeetingData, Long> {
}
