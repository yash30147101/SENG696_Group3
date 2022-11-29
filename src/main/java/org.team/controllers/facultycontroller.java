pack org.team.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team.exceptions.facultyNotFoundException;
import org.team.models.*;
import org.team.repositories.AppointmentRepository;
import org.team.repositories.studentRepository;
import org.team.repositories.facultyRepository;
import org.team.repositories.FacultyNotesRepository;
import org.team.services.facultyService;
import  org.team.utils.CommonUtils;
import  org.team.utils.Constrants;
import  org.team.utils.PdfUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class facultyController {
    private final facultyRepository facultyRepository;
    private final facultyService facultyService;

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private FacultyNotesRepository FacultyNotesRepository;

    @Autowired
    public facultyController(facultyRepository studentRepository, facultyService facultyService) {
        this.facultyRepository = studentRepository;
        this.facultyService = facultyService;
    }


    @PostMapping(value = "/FacultyNotes")
    public FacultyNotes FacultyNotes(@RequestBody FacultyNotesDto FacultyNotes) {
        System.out.println(FacultyNotes.toString());
        FacultyNotes FacultyNotes1 = new FacultyNotes();
        student student = studentRepository.findstudentByEmailEquals(FacultyNotes.getstudentEmail());
        faculty faculty = facultyRepository.findByFirstNameAndEmail(FacultyNotes.getFirstName(), FacultyNotes.getEmail());
        FacultyNotes1.setFacultyNotes(FacultyNotes.getFacultyNotes());
        FacultyNotes1.setfaculty(faculty);
        FacultyNotes1.setstudent(student);
        FacultyNotes1.setCreatedDate(Instant.now());

        return FacultyNotesRepository.save(FacultyNotes1);
    }

    @GetMapping(value = "/FacultyNotes", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String FacultyNotes(@RequestParam("dName") String dName, @RequestParam("dEmail") String dEmail, @RequestParam("pName") String pName, @RequestParam("pEmail") String pEmail) {


        Map<String, String> map = new HashMap<>();
        map.put(Constrants.faculty_NAME, dName);
        map.put(Constrants.faculty_EMAIl, dEmail);
        map.put(Constrants.student_NAME, pName);
        map.put(Constrants.student_EMAIL, pEmail);

        String body = CommonUtils.replaceText(Constrants.EMAIl_TEMPLATE_FacultyNotes, map);
        return body;
    }
    //  , produces = MediaType.TEXT_HTML_VALUE

    @GetMapping(value = "/FacultyNotes/pdf/{email}", produces =
            MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> employeeReport(@PathVariable("email") String email)
            throws IOException {
        System.out.println("email : " + email);
        Appointment appointment = appointmentRepository.findAppointmentsByfacultyEmailEqualsOrderByIdDesc(email).stream().findFirst().get();
        List<FacultyNotes> employees = FacultyNotesRepository.findByfaculty_EmailOrderByIdDesc(email);

        ByteArrayInputStream bis = PdfUtil.employeePDFReport(employees);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=students.pdf");

        return ResponseEntity.ok().headers(headers).contentType
                        (MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/faculty/all")
    public List<faculty> getfaculty() {
        return facultyRepository.findAll();
    }

    @GetMapping("/faculty/{id}")
    public faculty getfaculty(@PathVariable String id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new facultyNotFoundException(id));
    }

    @PostMapping("/faculty")
    public faculty newfaculty(@RequestBody faculty faculty) {
        return facultyRepository.save(faculty);
    }


    @GetMapping("/doc/all/spec/{specName}")
    public List<faculty> getfacultysBydepartment(@PathVariable String specName) {
        return facultyService.getfacultysWithdepartment(specName);
    }

    @PutMapping("/faculty/{id}")
    public faculty updatefaculty(@PathVariable String id, @RequestBody faculty updatefaculty) {
        return facultyRepository.findById(id)
                .map(faculty -> {
                    faculty.setUsername(updatefaculty.getUsername());
                    return facultyRepository.save(faculty);
                })
                .orElseThrow(() -> new facultyNotFoundException(id));
    }

    @DeleteMapping("faculty/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletefaculty(@PathVariable String id) {
        getfaculty(id);
        facultyRepository.deleteById(id);
    }

    @GetMapping("/faculty/user/{userName}") //done
    public faculty getfacultyByUsername(@PathVariable String userName) {
        return facultyRepository.findByUsername(userName);
    }
}

