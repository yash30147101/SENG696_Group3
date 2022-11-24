pack org.team.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import org.springframework.util.StringUtils;
import org.team.models.Appointment;
import org.team.models.student;
import org.team.models.priority;
import org.team.models.faculty;
import  org.team.utils.Constrants;
import  org.team.utils.EmailUtils;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class CompletionAgent extends Agent {


    String url = "jdbc:mysql://localhost:3306/mydatabase_new?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "test1234";
    Connection connection = null;

    @Override
    public void setup() {

        System.out.println("Connecting database inside Completion Agent...");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database connected!");
        addBehaviour(new TickerBehaviour(this, 17000) {

            @Override
            protected void onTick() {
                try {
                    System.out.println("complition agent=============started");
                    PreparedStatement statement = connection.prepareStatement("select * from appointment where status = 'Completed' and FacultyNotes IS NULL");
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
                        appointment.setEmail(resultSet.getString("email"));
                        appointment.setSms(resultSet.getString("sms"));
                        appointment.setFacultyNotes(resultSet.getString("FacultyNotes"));
                        appointment.setDateTime(resultSet.getTimestamp("datetime"));
                        PreparedStatement stat = connection.prepareStatement("SELECT * from student WHERE  id = ?");
                        stat.setLong(1, Long.parseLong(resultSet.getString("student_id")));
                        ResultSet rs = stat.executeQuery();
                        String studentName;
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
                            faculty.setPhone(Long.valueOf(rs1.getString("phone")));
                            appointment.setfaculty(faculty);
                        }
                        appointments.add(appointment);
                        System.out.println(appointment);
                    }
                    for (Appointment appointment : appointments) {
                        if (StringUtils.isEmpty(appointment.getFacultyNotes())) {
                            System.out.println("sending FacultyNotes mail");
                            if (appointment.getDateTime().before(new Date()) && appointment.getStatus().equalsIgnoreCase("Scheduled")) {
                                PreparedStatement stat3 = connection.prepareStatement("Update appointment set status = 'Completed' where id = ?");
                                stat3.setLong(1, appointment.getId());
                                stat3.executeUpdate();
                            }

                            String subject = "FacultyNotes for : " + appointment.getstudent().getFirstName() + " " + appointment.getstudent().getLastName();
                            Map<String, String> map = new HashMap<>();
                            map.put(Constrants.faculty_NAME, appointment.getfaculty().getFirstName());
                            map.put(Constrants.faculty_EMAIl, appointment.getfaculty().getEmail());
                            map.put(Constrants.student_NAME, appointment.getstudent().getFirstName());
                            map.put(Constrants.student_EMAIL, appointment.getstudent().getEmail());

                            String body = "click on link to provide FacultyNotes : http://localhost:8080/FacultyNotes?dName=" + appointment.getfaculty().getFirstName() + "&dEmail=" + appointment.getfaculty().getEmail() + "&pName=" + appointment.getstudent().getFirstName() + "&pEmail=" + appointment.getstudent().getEmail();
                            // System.out.println("faculty EMail : " + appointment.getfaculty().getEmail());
                            EmailUtils.main(appointment.getfaculty().getEmail(), subject, body);

                            PreparedStatement stat1 = connection.prepareStatement("update appointment set FacultyNotes = 'yes' WHERE  id = ?");
                            stat1.setLong(1, appointment.getId());
                            stat1.executeUpdate();
                        }


                    }
                    System.out.println("complition agent=============Ended");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

