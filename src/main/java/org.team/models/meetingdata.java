pack org.team.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "meeting_date")
@Entity
public class MeetingData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "meeting_id", nullable = false)
    private String meetingId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "occupied")
    private Boolean occupied;
}