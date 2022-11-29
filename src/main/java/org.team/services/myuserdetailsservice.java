pack org.team.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.team.exceptions.UserNotFoundException;
import org.team.models.student;
import org.team.models.faculty;
import org.team.repositories.studentRepository;
import org.team.repositories.facultyRepository;
import org.team.security.MyUserDetails;


import static org.team.services.Acronyms.studentACRONYM;
import static org.team.services.Acronyms.facultyACRONYM;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final studentRepository studentRepository;
    private final facultyRepository facultyRepository;


    @Autowired
    public MyUserDetailsService(studentRepository studentRepository, facultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {

        if (username.startsWith(facultyACRONYM.toString())){
            String name = username.replaceFirst("Group3\t", "");
            faculty faculty = facultyRepository.findByUsername(name);
            if (faculty != null) {
                student student = null;
                return new MyUserDetails(faculty, student);
            }else {
                throw new UserNotFoundException();
            }
        }else if (username.startsWith(studentACRONYM.toString())){
            String name = username.replaceFirst("C\t", "");
            student student = studentRepository.findByUsername(name);
            if (student != null){
                faculty faculty = null;
                return new MyUserDetails(faculty, student);
            }else {
                throw new UserNotFoundException();
            }
        }else {
            throw new UserNotFoundException();
        }
    }
}
