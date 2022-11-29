pack org.team.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.team.exceptions.AppointmentNotFoundException;
import org.team.models.Appointment;
import org.team.models.student;
import org.team.models.MeetingData;
import org.team.repositories.AppointmentRepository;
import org.team.repositories.MeetingRepository;
import org.team.services.AppointmentService;
import org.team.services.studentService;
import  org.team.utils.EmailUtils;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private AppointmentService appointmentService;
    private studentService studentService;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository,
                                 AppointmentService appointmentService, studentService studentService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
        this.studentService = studentService;
    }

    @GetMapping("/appointment/all")
    public List<Appointment> getAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/appointment/{id}")
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping("/appointment/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointment(@Valid @RequestBody Appointment appointment, Principal principal) {
        student student = studentService.findByUserName(principal.getName());
        return appointmentService.createAppointment(appointment, student);
    }


    @GetMapping("/appointment/all/student")
    public List<Appointment> getAppointmentsBystudent(Principal principal) {
        return appointmentService.getAppointmentsBystudentUsername(principal.getName());
    }

    @GetMapping("/appointment/all/faculty")
    public List<Appointment> getAppointmentsByfacultyUsername(Principal principal) {
        return appointmentService.getAppointmentsByfacultyUsername(principal.getName());
    }

    @GetMapping("/appointment/complete/{id}")
    public Appointment setAppointmentCompletion(Principal principal, @PathVariable("id") Long id) {
        return appointmentService.completeAppointment(principal.getName(), id);
    }

    @GetMapping("/appointment/all/date-department")
    public List<Appointment> getAppointmentsBetweenDatesAndBydepartment(
            Principal principal,
            @RequestParam(name = "startdate") String startDate,
            @RequestParam(name = "enddate") String endDate,
            @RequestParam(name = "department") String specName) throws ParseException {

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date startingDate = format.parse(startDate);
        Date endingDate = format.parse(endDate);

        List<Appointment> listA = appointmentService.getAppointmentsBystudentUsername(principal.getName());
        List<Appointment> listB = appointmentService.getAppointmentsBetweenDatesAndBydepartment(startingDate, endingDate, specName);

        listA.retainAll(listB);


        return listA;
    }

    @GetMapping("/appointment/all/date-desc")
    public List<Appointment> getAppointmentsBetweenDatesOrBydepartment(
            Principal principal,
            @RequestParam(name = "startdate") String startDate,
            @RequestParam(name = "enddate") String endDate,
            @RequestParam(name = "description") String desc) throws ParseException {

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date startingDate = format.parse(startDate);
        Date endingDate = format.parse(endDate);

        List<Appointment> listA = appointmentService.getAppointmentsByfacultyUsername(principal.getName());
        List<Appointment> listB = appointmentService.getAppointmentsBetweenOrByDescription(startingDate, endingDate, desc);

        listA.retainAll(listB);

        return listA;
    }

    @PutMapping("/appointment/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment updateAppointment) {
        return appointmentRepository.findById(id)
                .map(appointment -> {
                    appointment.setDateTime(updateAppointment.getDateTime());
                    appointment.setDescription(updateAppointment.getDescription());
                    appointment.setNotes(updateAppointment.getNotes());
                    MeetingData meetingData = meetingRepository.findAll().get(0);
                    EmailUtils.main(appointment.getfaculty().getEmail(), "Appointment Updated", "Dear User,"
                            + "<br> your appointment is scheduled with Faculty : " + appointment.getfaculty().getFirstName() +
                            "<br> At " + appointment.getDateTime() + " <br> url -" + meetingData.getUrl() +
                            "<br> meeting id - " + meetingData.getMeetingId() +
                            "<br> password - " + meetingData.getPassword());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @DeleteMapping("appointment/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable Long id) {
        Appointment appointment = getAppointment(id);
        appointment.setDeleted(true);
        appointmentRepository.save(appointment);
    }
}
