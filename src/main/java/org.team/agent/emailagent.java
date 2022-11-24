pack org.team.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import org.springframework.util.StringUtils;
import org.team.models.*;
import  org.team.utils.EmailUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailAgent extends Agent {

    String url = "jdbc:mysql://localhost:3306/mydatabase_new?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "test1234";
    Connection connection = null;

    @Override
    public void setup() {

        System.out.println("Connecting database inside Email Agent...");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database connected!");
        addBehaviour(new TickerBehaviour(this, 12000) {

            @Override
            protected void onTick() {
                try {
                    String subject = "Appointment Booked";
                    ACLMess msg = receive();
                    if (msg != null) {
                        Appointment appointment = (Appointment) msg.getContentObject();
                        if (appointment != null) {
                            PreparedStatement statement1 = connection.prepareStatement("select * from meeting_date");
                            ResultSet resultSet1 = statement1.executeQuery();
                            MeetingData meetingData = new MeetingData();
                            while (resultSet1.next()) {
                                meetingData.setUrl(resultSet1.getString("url"));
                                meetingData.setPassword(resultSet1.getString("password"));
                                meetingData.setMeetingId(resultSet1.getString("meeting_id"));
                            }

                            EmailUtils.main(appointment.getstudent().getEmail(), subject, "Dear " + appointment.getstudent().getFirstName()
                                    + ",<br> your appointment is scheduled with Faculty : " + appointment.getfaculty().getFirstName() + " " + appointment.getfaculty().getLastName() +
                                    "<br> At " + appointment.getDateTime() + " <br> url -" + meetingData.getUrl() +
                                    "<br> meeting id - " + meetingData.getMeetingId() +
                                    "<br> password - " + meetingData.getPassword());
                            PreparedStatement stat1 = connection.prepareStatement("update appointment set email = 'yes' WHERE  id = ?");
                            stat1.setLong(1, appointment.getId());
                            stat1.executeUpdate();


                            EmailUtils.main(appointment.getfaculty().getEmail(), subject, "Dear " + appointment.getfaculty().getFirstName()
                                    + ",<br> your appointment is scheduled with student : " + appointment.getstudent().getFirstName() + " " + appointment.getstudent().getLastName() +
                                    "<br> At " + appointment.getDateTime() + " <br> url -" + meetingData.getUrl() +
                                    "<br> meeting id - " + meetingData.getMeetingId() +
                                    "<br> password - " + meetingData.getPassword());
                            PreparedStatement stat2 = connection.prepareStatement("update appointment set email = 'yes' WHERE  id = ?");
                            stat2.setLong(1, appointment.getId());
                            stat2.executeUpdate();
                        }
                    } else {
                        System.out.println("EMAIL agent=============started");
                        PreparedStatement statement = connection.prepareStatement("select * from appointment where status = 'Scheduled'");
                        statement.execute();
                        ResultSet resultSet = statement.getResultSet();
                        List<Appointment> appointments = new ArrayList<Appointment>();
                        while (resultSet.next()) {
                            Appointment appointment = new Appointment();
                            appointment.setId(Long.valueOf(resultSet.getString("id")));
                            appointment.setpriority(priority.parse(resultSet.getInt("priority")));
                            appointment.setStatus(resultSet.getString("status"));
                            appointment.setDescription(resultSet.getString("description"));
                            appointment.setNotes(resultSet.getString("notes"));
                            appointment.setAge(resultSet.getString("age"));
                            appointment.set(resultSet.getString(""));
                            appointment.setEmail(resultSet.getString("email"));
                            appointment.setDateTime(resultSet.getTimestamp("datetime"));
                            PreparedStatement stat = connection.prepareStatement("SELECT * from student WHERE  id = ?");
                            stat.setLong(1, Long.parseLong(resultSet.getString("student_id")));
                            ResultSet rs = stat.executeQuery();
                            while (rs.next()) {
                                student student = new student();
                                student.setEmail(rs.getString("email"));
                                student.setUsername(rs.getString("username"));
                                student.setFirstName(rs.getString("first_name"));
                                student.setLastName(rs.getString("last_name"));
                                student.setId(rs.getString("id"));
                                student.setPhone(Long.valueOf(rs.getString("phone")));
                                appointment.setstudent(student);
                            }

                            PreparedStatement stat1 = connection.prepareStatement("SELECT * from faculty WHERE  id = ?");
                            stat1.setLong(1, Long.parseLong(resultSet.getString("faculty_id")));
                            ResultSet rs1 = stat1.executeQuery();
                            while (rs1.next()) {
                                faculty faculty = new faculty();
                                faculty.setEmail(rs1.getString("email"));
                                faculty.setUsername(rs1.getString("username"));
                                faculty.setFirstName(rs1.getString("first_name"));
                                faculty.setLastName(rs1.getString("last_name"));
                                faculty.setId(rs1.getString("id"));
                                faculty.setPhone(Long.parseLong(rs1.getString("phone")));
                                appointment.setfaculty(faculty);
                            }
                            appointments.add(appointment);
                            System.out.println(appointment);
                        }
                        for (Appointment appointment : appointments) {
                            if (StringUtils.isEmpty(appointment.getEmail())) {
                                System.out.println("sending mail");

                                PreparedStatement statement1 = connection.prepareStatement("select * from meeting_date");
                                ResultSet resultSet1 = statement1.executeQuery();
                                MeetingData meetingData = new MeetingData();
                                while (resultSet1.next()) {
                                    meetingData.setUrl(resultSet1.getString("url"));
                                    meetingData.setPassword(resultSet1.getString("password"));
                                    meetingData.setMeetingId(resultSet1.getString("meeting_id"));
                                }
                                EmailUtils.main(appointment.getstudent().getEmail(), subject, "Dear " + appointment.getstudent().getFirstName()
                                        + ",<br> your appointment is scheduled with Faculty : " + appointment.getfaculty().getFirstName() + " " + appointment.getfaculty().getLastName() +
                                        "<br> At " + appointment.getDateTime() + " <br> url -" + meetingData.getUrl() +
                                        "<br> meeting id - " + meetingData.getMeetingId() +
                                        "<br> password - " + meetingData.getPassword());
                                PreparedStatement stat1 = connection.prepareStatement("update appointment set email = 'yes' WHERE  id = ?");
                                stat1.setLong(1, appointment.getId());
                                stat1.executeUpdate();
                            }
                        }
                        System.out.println("EMAIL agent=============Ended");
                    }
                } catch (SQLException | UnreadableException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
