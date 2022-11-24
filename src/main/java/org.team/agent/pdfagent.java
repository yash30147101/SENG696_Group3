pack org.team.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import org.team.models.student;
import org.team.models.faculty;
import org.team.models.FacultyNotes;
import  org.team.utils.EmailUtils;

import java.sql.*;

public class PdfAgent extends Agent {

    String url = "jdbc:mysql://localhost:3306/mydatabase_new?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "test1234";
    Connection connection = null;

    @Override
    public void setup() {

        System.out.println("Connecting database inside PDF Agent...");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database connected!");
        addBehaviour(new TickerBehaviour(this, 19000) {

            @Override
            protected void onTick() {
                try {
                    System.out.println("PDF agent=============started");
                    PreparedStatement statement = connection.prepareStatement("select * from FacultyNotes where mail_send is null");
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        FacultyNotes FacultyNotes = new FacultyNotes();
                        FacultyNotes.setId(resultSet.getLong("id"));
                        FacultyNotes.setFacultyNotes(resultSet.getString("FacultyNotes"));
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
                            FacultyNotes.setstudent(student);
                        }

                        PreparedStatement stat1 = connection.prepareStatement("SELECT * from faculty WHERE  id = ?");
                        stat1.setLong(1, Long.parseLong(resultSet.getString("faculty_id")));
                        ResultSet rs1 = stat1.executeQuery();
                        while (rs1.next()) {
                            faculty faculty = new faculty();
                            faculty.setEmail(rs1.getString("email"));
                            faculty.setUsername(rs1.getString("username"));
                            faculty.setFirstName(rs1.getString("first_name"));
                            faculty.setId(rs1.getString("id"));
                            faculty.setPhone(Long.valueOf(rs1.getString("phone")));
                            FacultyNotes.setfaculty(faculty);
                        }
                        String subject = "FacultyNotes: Pdf has been generated!! click below link to generate PDF";
                        String body = "http://localhost:8080/FacultyNotes/pdf/" + FacultyNotes.getfaculty().getEmail();
                        EmailUtils.main(FacultyNotes.getstudent().getEmail(), subject, body);
                        PreparedStatement stat2 = connection.prepareStatement("update FacultyNotes set mail_send = 'yes' WHERE  id = ?");
                        stat2.setLong(1, FacultyNotes.getId());
                        stat2.executeUpdate();
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println("PDF agent=============Ended");

            }

        });
    }
}
