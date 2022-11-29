pack  org.team.utils;

public class Constrants {

    public static final String EMAIl_TEMPLATE_FacultyNotes = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <script src=\"https://code.jquery.com/jquery-3.3.1.min.js\"\n" +
            "    integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<style>\n" +
            "body {font-family: Arial, Helvetica, sans-serif;}\n" +
            "* {box-sizing: border-box;}\n" +
            "\n" +
            "input[type=text], select, textarea {\n" +
            "  width: 100%;\n" +
            "  padding: 12px;\n" +
            "  border: 1px solid #ccc;\n" +
            "  border-radius: 4px;\n" +
            "  box-sizing: border-box;\n" +
            "  margin-top: 6px;\n" +
            "  margin-bottom: 16px;\n" +
            "  resize: vertical;\n" +
            "}\n" +
            "\n" +
            "input[type=submit] {\n" +
            "  background-color: #04AA6D;\n" +
            "  color: white;\n" +
            "  padding: 12px 20px;\n" +
            "  border: none;\n" +
            "  border-radius: 4px;\n" +
            "  cursor: pointer;\n" +
            "}\n" +
            "\n" +
            "input[type=submit]:hover {\n" +
            "  background-color: #45a049;\n" +
            "}\n" +
            "\n" +
            ".container {\n" +
            "  border-radius: 5px;\n" +
            "  background-color: #f2f2f2;\n" +
            "  padding: 20px;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h3>FacultyNotes Form</h3>\n" +
            "\n" +
            "<div class=\"container\">\n" +
            "  <form action=\"http://localhost:8080/FacultyNotes\"  method=\"post\" >\n" +
            "    <label for=\"fname\">Faculty Name</label>\n" +
            "    <input type=\"text\" id=\"fname\" name=\"firstName\" value=\"{faculty.name}\" disabled>\n" +
            "    \n" +
            "    <label for=\"lname\">Faculty email</label>\n" +
            "    <input type=\"text\" id=\"email\" name=\"email\" value=\"{faculty.email}\" disabled>\n" +
            "\n" +
            "    <label for=\"fname\">Student Name</label>\n" +
            "    <input type=\"text\" id=\"pname\" name=\"pName\" value=\"{student.name}\" disabled>\n" +
            "    \n" +
            "    <label for=\"lname\">Student email</label>\n" +
            "    <input type=\"text\" id=\"pemail\" name=\"pEmail\" value=\"{student.email}\" disabled>\n" +
            "\n" +
            "    <label for=\"FacultyNotes\">Faculty Notes</label>\n" +
            "    <textarea id=\"FacultyNotes\" name=\"FacultyNotes\" placeholder=\"Write something..\" style=\"height:200px\"></textarea>\n" +
            "\n" +
            "    <input type=\"button\" onclick=\"func()\" value=\"Submit\">\n" +
            "  </form>\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "<script>\n" +
            "  function func(){\n" +
            "    var facultyName = document.getElementById(\"fname\").defaultValue;\n" +
            "    var facultyEmail = document.getElementById(\"email\").defaultValue;\n" +
            "    var studentName = document.getElementById(\"pname\").defaultValue;\n" +
            "    var studentEmail = document.getElementById(\"pemail\").defaultValue;\n" +
            "    var FacultyNotes = document.getElementById(\"FacultyNotes\").value;\n" +
            "    console.log(facultyName,facultyEmail,studentName,studentEmail,FacultyNotes);\n" +
            "\n" +
            "     let updatedata = {\n" +
            "      \"firstName\": facultyName,\n" +
            "      \"email\": facultyEmail,\n" +
            "      \"studentName\": studentName,\n" +
            "      \"studentEmail\" : studentEmail,\n" +
            "      \"FacultyNotes\": FacultyNotes\n" +
            "    };\n" +
            "\n" +
            "    // var xhr = new XMLHttpRequest();\n" +
            "    // xhr.open(\"POST\", \"http://localhost:8080/FacultyNotes\", true);\n" +
            "    // xhr.setRequestHeader('Content-Type', 'application/json');\n" +
            "    // xhr.send(JSON.stringify(updatedata));\n" +
            "\n" +
            "    $.ajax({\n" +
            "      url: \"http://localhost:8080/FacultyNotes\",\n" +
            "      type: 'POST',\n" +
            "      data: JSON.stringify(updatedata),\n" +
            "      dataType: 'json',\n" +
            "      crossDomain: true,\n" +
            "      contentType: 'application/json',\n" +
            "      success: function (data) {\n" +
            "        \n" +
            "        if(confirm(\"data saved successfully\")) document.location = 'http://localhost:8080/';\n" +
            "      },\n" +
            "      statusCode: {\n" +
            "        500: function () {\n" +
            "          alert(\"Invalid data!\");\n" +
            "        }\n" +
            "      }\n" +
            "    });\n" +
            "     if(confirm(\"confirm\")) document.location = 'http://localhost:8080/';\n" +
            "\n" +
            "\n" +
            "  }\n" +
            "</script>\n" +
            "</html>\n";

    public static final String faculty_NAME = "\\{faculty.name\\}";
    public static final String faculty_EMAIl = "\\{faculty.email\\}";
    public static final String student_NAME = "\\{student.name\\}";
    public static final String student_EMAIL = "\\{student.email\\}";

    public static final String NAME = "\\{Name\\}";
}
