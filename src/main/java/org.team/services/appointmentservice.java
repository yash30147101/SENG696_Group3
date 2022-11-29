pack org.team.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.exceptions.AppointmentNotFoundException;
import org.team.models.Appointment;
import org.team.models.student;
import org.team.repositories.AppointmentRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final facultyService facultyService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, facultyService facultyService) {
        this.appointmentRepository = appointmentRepository;
        this.facultyService = facultyService;
    }

    public Appointment createAppointment(Appointment appointment, student student) {

        Appointment newAppointment = new Appointment();
        student.set(appointment.get());
        student.setAge(appointment.getAge());
        newAppointment.setAge(student.getAge());
        newAppointment.set(student.get());
        newAppointment.setstudent(student);
        newAppointment.setfaculty(facultyService.findfacultyByAmka(appointment.getfaculty().getId()));
        newAppointment.setDateTime(appointment.getDateTime());
        newAppointment.setDescription(appointment.getDescription());
        newAppointment.setNotes(appointment.getNotes());
        newAppointment.setpriority(appointment.getpriority());
        newAppointment.setStatus("NOT_SCHEDULED");
        newAppointment.setDeleted(Boolean.FALSE);

        appointmentRepository.save(newAppointment);
        return newAppointment;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll().stream().filter(d -> d.getDeleted() == Boolean.FALSE).collect(Collectors.toList());
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).filter(d -> d.getDeleted() == Boolean.FALSE)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    public List<Appointment> getAppointmentsBystudentUsername(String username) {

        List<Appointment> appointments = appointmentRepository.findAppointmentsBystudentUsernameEqualsOrderByIdDesc(username).stream().filter(d -> d.getDeleted() == Boolean.FALSE).collect(Collectors.toList());
        //appointments.sort((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        return appointments;
    }

    public List<Appointment> getAppointmentsByfacultyUsername(String username) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByfacultyUsernameEqualsOrderByIdDesc(username).stream().filter(d -> d.getDeleted() == Boolean.FALSE).collect(Collectors.toList());
        // appointments.sort((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        return appointments;
    }

    public List<Appointment> getAppointmentsBetweenDatesAndBydepartment(Date startDate, Date endDate, String specName) {
        return appointmentRepository.findAppointmentsByDateTimeBetweenAndfaculty_department_Name(startDate, endDate, specName).stream().filter(d -> d.getDeleted() == Boolean.FALSE).collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsBetweenOrByDescription(Date startDate, Date endDate, String desc) {
        return appointmentRepository.findAppointmentsByDateTimeBetweenAndDescriptionContaining(startDate, endDate, desc);
    }


    public Appointment completeAppointment(String name, Long id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setStatus("Completed");
        return appointmentRepository.save(appointment);
    }
}
