pack org.team.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import org.springframework.util.StringUtils;
import org.team.models.*;
import  org.team.utils.SmsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SmsAgent extends Agent {


    String url = "jdbc:mysql://localhost:3306/mydatabase_new?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "test1234";
    Connection connection = null;

    @Override
    public void setup() {

        System.out.println("Connecting database inside SMS Agent...");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database connected!");
        addBehaviour(new TickerBehaviour(this, 15000) {

            @Override
            protected void onTick() {
                try {
                    System.out.println("SMS agent=============started");
                    PreparedStatement statement = connection.prepareStatement("select * from appointment where status = 'Scheduled'");
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    List<Appointment> appointments = new ArrayList<Appointment>();
                    while (resultSet.next()) {
                        Appointment appointment = new Appointment();
                        appointment.setId(Long.valueOf(resultSet.getString("id")));
                        //System.out.println("Appointment DateTime in SMS : " + resultSet.getDate("dateTime"));
                        appointment.setDateTime(resultSet.getTimestamp("dateTime"));
                        appointment.setDescription(resultSet.getString("description"));
                        appointment.setNotes(resultSet.getString("notes"));
                        appointment.setpriority(priority.parse(resultSet.getInt("priority")));
                        appointment.setStatus(resultSet.getString("status"));
                        appointment.setDescription(resultSet.getString("description"));
                        appointment.setNotes(resultSet.getString("notes"));
                        appointment.setEmail(resultSet.getString("email"));
                        appointment.setSms(resultSet.getString("sms"));
                        PreparedStatement stat = connection.prepareStatement("SELECT * from student WHERE  id = ?");
                        stat.setLong(1, Long.parseLong(resultSet.getString("student_id")));
                        ResultSet rs = stat.executeQuery();
                        while (rs.next()) {
                            student student = new student();
                            student.setEmail(rs.getString("email"));
                            student.setUsername(rs.getString("username"));
                            student.setFirstName(rs.getString("first_name"));
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
                        if (StringUtils.isEmpty(appointment.getSms())) {
                            System.out.println("sending sms");
                            PreparedStatement statement1 = connection.prepareStatement("select * from meeting_date");
                            ResultSet resultSet1 = statement1.executeQuery();
                            MeetingData meetingData = new MeetingData();
                            while (resultSet1.next()) {
                                meetingData.setUrl(resultSet1.getString("url"));
                                meetingData.setPassword(resultSet1.getString("password"));
                                meetingData.setMeetingId(resultSet1.getString("meeting_id"));
                            }
                            SmsUtil.main("Dear " + appointment.getstudent().getFirstName()
                                    + ",\n your appointment is scheduled with Faculty : " + appointment.getfaculty().getFirstName() + " " + appointment.getfaculty().getLastName() +
                                    "\n At " + appointment.getDateTime() + " \n url -" + meetingData.getUrl() +
                                    "\n meeting id - " + meetingData.getMeetingId() +
                                    "\n password - " + meetingData.getPassword(), appointment.getstudent().getPhone());
                            PreparedStatement stat1 = connection.prepareStatement("update appointment set sms = 'yes' WHERE  id = ?");
                            stat1.setLong(1, appointment.getId());
                            stat1.executeUpdate();
                        }


                    }
                    System.out.println("SMS agent=============ENDED");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

