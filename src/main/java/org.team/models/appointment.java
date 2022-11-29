pack org.team.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "appointmentEntity")
@Table(name = "appointment")
public class Appointment implements Serializable {


    private Long id;

    private student student;

    @NotNull
    private faculty faculty;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm")
    private Date dateTime;

    @NotNull
    private String description;

    @Nullable
    @Size(max = 250)
    private String notes;

    @Nullable
    private Integer priority;

    @Nullable
    @Size(max = 250)
    private String status;

    @Nullable
    private String email;

    @Nullable
    private String sms;

    @Nullable
    private String ;

    @Nullable
    private String age;

    @Nullable
    private String FacultyNotes;

    @Column(name = "id_deleted")
    private Boolean isDeleted = false;

    public Appointment() {
    }

    public Appointment(student student, faculty faculty, Date dateTime, String description, String notes) {
        this.student = student;
        this.faculty = faculty;
        this.dateTime = dateTime;
        this.description = description;
        this.notes = notes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "student_id")
    public student getstudent() {
        return student;
    }

    public void setstudent(student student) {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    public faculty getfaculty() {
        return faculty;
    }

    public void setfaculty(faculty faculty) {
        this.faculty = faculty;
    }

    @Column(name = "datetime")
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "notes")
    @Nullable
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Nullable
    public priority getpriority() {
        return priority.parse(this.priority);
    }

    public void setpriority(@Nullable priority priority) {
        this.priority = priority.getValue();
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getSms() {
        return sms;
    }

    public void setSms(@Nullable String sms) {
        this.sms = sms;
    }

    @Nullable
    public String getFacultyNotes() {
        return FacultyNotes;
    }

    public void setFacultyNotes(@Nullable String FacultyNotes) {
        this.FacultyNotes = FacultyNotes;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", student=" + student +
                ", faculty=" + faculty +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", notes='" + notes + '\'' +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", sms='" + sms + '\'' +
                ", FacultyNotes='" + FacultyNotes + '\'' +
                '}';
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Nullable
    public String get() {
        return ;
    }

    public void set(@Nullable String ) {
        this. = ;
    }

    @Nullable
    public String getAge() {
        return age;
    }

    public void setAge(@Nullable String age) {
        this. = age;
    }
}
