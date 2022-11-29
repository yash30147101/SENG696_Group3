pack org.team.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.team.exceptions.studentAmkaExistsException;
import org.team.exceptions.studentEmailExistsException;
import org.team.exceptions.studentNotFoundException;
import org.team.exceptions.studentParamsException;
import org.team.models.student;
import org.team.repositories.studentRepository;
import org.team.services.studentService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class studentController {

    private final studentRepository studentRepository;
    private studentService studentService;

    @Autowired
    public studentController(studentRepository studentRepository, studentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public student registerAccount(@Valid @RequestBody student student) throws studentEmailExistsException, studentAmkaExistsException, studentParamsException {
        return studentService.registerUserIfIsValid(student);
    }

    @GetMapping("/students")
    public List<student> getstudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}") //done
    public student getstudent(@PathVariable String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new studentNotFoundException(id));
    }

    @GetMapping("/students/user/{userName}") //done
    public student getstudentByUserName(@PathVariable String userName) {
        return studentRepository.findByUsername(userName);
    }

    @PutMapping("/students/{id}")
    public student updatestudent(@PathVariable String id, @PathVariable student updatestudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setUsername(updatestudent.getUsername());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new studentNotFoundException(id));
    }

    @DeleteMapping("students/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletestudent(@PathVariable String id) {
        getstudent(id);
        studentRepository.deleteById(id);
    }
}

