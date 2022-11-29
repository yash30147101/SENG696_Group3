pack org.team.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "FacultyNotes")
@Data
public class FacultyNotes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "FacultyNotes", nullable = false)
    private String FacultyNotes;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private student student;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @NotNull
    private faculty faculty;

    @Column(name = "mail_send")
    private String mailSend;

    @Column(name = "created_date")
    private Instant createdDate;


}
