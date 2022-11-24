pack org.team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.team.repositories.studentRepository;
import org.team.repositories.facultyRepository;
import org.team.repositories.departmentRepository;


@Configuration
public class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(studentRepository studentRepository, facultyRepository facultyRepository,
                                   departmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        return args -> {
        };
    }
}