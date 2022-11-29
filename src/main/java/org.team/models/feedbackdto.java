pack org.team.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class FacultyNotesDto implements Serializable {

    private String firstName;
    private String email;
    private String studentName;
    private String studentEmail;
    private String FacultyNotes;

}
