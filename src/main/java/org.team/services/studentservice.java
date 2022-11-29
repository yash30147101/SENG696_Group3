pack org.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team.exceptions.studentAmkaExistsException;
import org.team.exceptions.studentEmailExistsException;
import org.team.exceptions.studentParamsException;
import org.team.models.student;
import org.team.repositories.studentRepository;

@Service
public class studentService {

    private final studentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public studentService(studentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public student findByUserName(String username){
        return studentRepository.findByUsername(username);
    }

    public student registerstudent(student student) {
        student newstudent = new student();
        newstudent.setFirstName(student.getFirstName());
        newstudent.setLastName(student.getLastName());
        newstudent.setId(student.getId());
        newstudent.setPhone(student.getPhone());
        newstudent.setEmail(student.getEmail());
        newstudent.setUsername(student.getUsername());
        newstudent.setPassword(passwordEncoder.encode(student.getPassword()));

        studentRepository.save(newstudent);
        return newstudent;
    }

    public boolean validUserEmail(student student) {
        return studentRepository.findstudentByEmailEquals(student.getEmail()) == null;
    }

    public boolean validUserId(student student) {
        return studentRepository.findstudentByIdEquals(student.getId()) == null;
    }

    public student registerUserIfIsValid(student student) throws studentEmailExistsException, studentAmkaExistsException, studentParamsException {

        if (validUserId(student) && validUserEmail(student)) {
            registerstudent(student);
            return student;
       } else if (!validUserEmail(student) && validUserId(student)) {
            throw new studentEmailExistsException(student.getEmail());
        }else if (validUserEmail(student) && !validUserId(student)) {
            throw new studentAmkaExistsException(student.getId());
        }else {
            throw new studentParamsException();
        }
    }

}
