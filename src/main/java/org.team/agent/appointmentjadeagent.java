pack org.team.agent;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.team.models.Appointment;
import org.team.models.student;
import org.team.models.priority;
import org.team.models.faculty;

import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentJadeAgent extends Agent {

    String url = "jdbc:mysql://localhost:3306/mydatabase_new?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "test1234";
    Connection connection = null;

    @Autowired
    private JdbcTemplate appointmentRepository;

    @Override
    public void setup() {

        System.out.println("Connecting database inside Appointment Agent...");


        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database connected!");


        addBehaviour(new TickerBehaviour(this, 10000) {
            @Override
            protected void onTick() {
                try {
                    //appointmentRepository.execute("select * from Appointment",Appointment.class)
                    System.out.println("Appointment agent=============started");
                    PreparedStatement statement = connection.prepareStatement("select * from appointment where status = 'Not_Scheduled'");
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
                        appointment.setDateTime(resultSet.getTimestamp("datetime"));
                        System.out.println("Date Time : " + resultSet.getTimestamp("datetime"));
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

                        PreparedStatement stat1 = connection.prepareStatement("SELECT * from faculty where  id = ?");
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
                        boolean match = false;
                        if (appointment.getpriority() == priority.URGENT)
                            System.out.println("handling Urgent priority");
                        while (match) {
                            Optional<Appointment> appointment1 = appointments.stream().filter(d -> d.getDateTime().equals(appointment.getDateTime())).findAny();
                            if (appointment1.isPresent()) {
                                appointment.setDateTime(Date.from(appointment.getDateTime().toInstant().plus(1, ChronoUnit.HOURS)));
                                match = true;
                            } else {
                                match = false;
                            }

                        }
                        PreparedStatement stat2 = connection.prepareStatement("Update appointment set datetime = ? where id = ?");
                        stat2.setTimestamp(1, new Timestamp(appointment.getDateTime().getTime()));
                        stat2.setLong(2, appointment.getId());
                        stat2.executeUpdate();

                        if (appointment != null) {
                            PreparedStatement stat3 = connection.prepareStatement("Update appointment set status = 'Scheduled' where id = ?");
                            stat3.setLong(1, appointment.getId());
                            stat3.executeUpdate();

                        }

                        ACLMess aclmsg = new ACLMessage(ACLMessage.REQUEST);
                        aclmsg.addReceiver(new AID("EmailAgent", AID.ISLOCALNAME));
                        aclmsg.setContentObject(appointment);
                        send(aclmsg);

                    }
                    System.out.println("Appointment agent=============Ended");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
