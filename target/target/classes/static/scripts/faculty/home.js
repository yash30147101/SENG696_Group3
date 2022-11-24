function logout() {
    sessionStorage.removeItem(SESSION_STORAGE_LOGIN_TOKEN_NAME);
    window.location.replace(ROOT_PATH + "/logout")
}
function populateDataTable(appointments) {
             if ( $.fn.DataTable.isDataTable('#appointments') ) {
                   $('#appointments').DataTable().destroy();
              }
            $('#appointments tbody').empty();
            console.log("apointment=============",appointments)
            $("#appointments").append("<tbody>");
            jQuery.each(appointments, function(i,appointment) {
                
                $("#appointments").append("<tr id='appointmentRow" + appointment.id + "'><td>" + appointment.id + "</td><td>" + appointment.student.firstName + "</td><td>" +appointment.dateTime + "</td><td>" + appointment.description +  "</td><td>" + appointment.status +"</td><td><span id='ClickableImageEdit" + appointment.id + "' data-toggle='modal' data-target='#viewAppointmentModal' onclick='findRow()' style='color: #0067B3'>View details</span></td><td><button id='complete" + appointment.id + "' onclick='show(" + appointment.id + ")' class='custom-btn' >Completed</button></td></tr>");
                if(appointment.status === 'Completed'){
                    document.getElementById("complete"+appointment.id).disabled = 'disabled'
                }

             });
           $("#appointments").append("</tbody>");

            // $('#appointments').DataTable({
            //        "bFilter": false,
            //        "columnDefs": [
            //          { "orderable": true, "targets": 3 },
            //                ]
            //       });

}

function show(id){
 $.ajax({
        url: ROOT_PATH + "/appointment/complete/"+id
    }).then(function(appointment) {
        document.getElementById("complete"+id).disabled = 'disabled'
        window.location.reload();
        
    });
}


var tableRow;
function findRow(id){
      $("#appointments tr").click(function() {
       tableRow=$(this).children("td").html();
       loadAppointment(tableRow);
    });
}

function loadAppointment(id) {
       let appointment=$.grep(appointmentsTable, function(e){ return e.id == id; });
       let appointmentDay=appointment[0].dateTime.split(" ");
       $("input[name=date]").val(appointmentDay[0]);
       $("input[name=time]").val(appointmentDay[1]);
       $(":input[name=briefdescription]").val(appointment[0].description);
       $(":input[name=notes]").val(appointment[0].notes);
       $("input[name=fname]").val(appointment[0].student.firstName);
       $("input[name=lname]").val(appointment[0].student.lastName);
       $("input[name=id]").val(appointment[0].student.id);
       $("input[name=phone]").val(appointment[0].student.phone);
       $("input[name=email]").val(appointment[0].student.email);
}

var appointmentsTable;
$(document).ready(function() {
   let json = JSON.parse(sessionStorage.getItem(SESSION_STORAGE_LOGIN_TOKEN_NAME));
   let userw=json.userName;
   document.getElementById("welcome").innerHTML = "You are connected as " + userw;
   document.getElementById("name").innerHTML = userw;
   document.getElementById("email").innerHTML = "yashtrada111@gmail.com"
    $.ajax({
        url: ROOT_PATH + "/faculty/user/"+userw
    }).then(function(faculty) {
        document.getElementById("name").innerHTML = faculty.firstName;
        document.getElementById("email").innerHTML = faculty.email;
    });

   $.ajax({
        url: ROOT_PATH + "/appointment/all/faculty"
    }).then(function(appointments) {
        appointmentsTable=appointments;
        populateDataTable(appointments);
    });

  $("#searchAppointmentButton").on('click', function(event){
          event.preventDefault();
          let descriptionToSearch=$("#briefdescriptionS").val();
          let datesToSearch=$("input[name=dates]").val().split(" ");
          let startds = datesToSearch[0];
          let endds = datesToSearch[2];
           $.ajax({
               url:  ROOT_PATH + '/appointment/all/date-desc?description='+descriptionToSearch+'&startdate='+startds+" 00:00"+'&enddate='+endds+" 24:00",
               success: function(data){
                appointmentsTable=data;
                 $('#SearchModal').modal('hide');
                 populateDataTable(data);
                  },
                   statusCode: {
                       401 : function() {
                           alert("Invalid data!");
                       }
                   }
               });
   });


});
