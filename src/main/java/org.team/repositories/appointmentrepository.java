pack org.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.models.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment save(Appointment appointment);

    List<Appointment> findAppointmentsBystudentUsernameEquals(String username);

    List<Appointment> findAppointmentsByfacultyUsernameEquals(String username);

    List<Appointment> findAppointmentsByDateTimeBetweenAndfaculty_department_Name(Date startDate, Date endDate, String specName);

    List<Appointment> findAppointmentsByDateTimeBetweenAndDescriptionContaining(Date startDate, Date endDate, String description);


    //@Query(value = "select a from Appointment a where a.student.username = :username and a.isDeleted = 0 order by a.datetime desc")
    List<Appointment> findAppointmentsBystudentUsernameEqualsOrderByIdDesc(String username);

    // @Query(value = "select a from Appointment a where a.faculty.username = :username and a.isDeleted = 0 order by a.datetime desc")
    List<Appointment> findAppointmentsByfacultyUsernameEqualsOrderByIdDesc(String username);

    List<Appointment> findAppointmentsByfacultyEmailEqualsOrderByIdDesc(String username);
}
