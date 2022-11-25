function logout() {
    sessionStorage.removeItem(SESSION_STORAGE_LOGIN_TOKEN_NAME);
    window.location.replace(ROOT_PATH + "/logout")
}
function populateDataTableAndUpdate(appointments) {
             let getToday = new Date();
             const day=getToday.getDate();
             const month=getToday.getMonth()+1;
             const year=getToday.getFullYear();
             const today=month+ "-"+ day + "-" + year;
             if ( $.fn.DataTable.isDataTable('#appointments') ) {
                   $('#appointments').DataTable().destroy();
              }
            $('#appointments tbody').empty();
            $("#appointments").append("<tbody>");
            jQuery.each(appointments, function(i,appointment) {
             let appointmentTime=appointment.dateTime.split(" ");
             let dappointment = Date.parse(appointmentTime[0]);
             let dtoday = Date.parse(today);

            if (dappointment<=dtoday){
               $("#appointments").append("<tr id='appointmentRow" + appointment.id + "'><td>" + appointment.id + "</td><td>" + appointment.faculty.department.name + "</td><td>" + appointment.dateTime + "</td><td><i id='NoClickableImage' class='fa fa-pencil-square-o' aria-hidden='true'></i></td><td><i id='NoClickableImage' class='fa fa-ban' aria-hidden='true'></i></td></tr>");

            }
            else{
                $("#appointments").append("<tr id='appointmentRow" + appointment.id + "'><td>" + appointment.id + "</td><td>" +appointment.faculty.department.name + "</td><td>" + appointment.dateTime + "</td><td><i id='ClickableImageEdit' class='fa fa-pencil-square-o' aria-hidden='true' onclick='print()'></i></td><td><i id='ClickableImageCancel' class='fa fa-ban' aria-hidden='true' data-toggle='modal' data-target='#deleteModal' onclick='findRow()'></i></td></tr>");

            }

             });
           $("#appointments").append("</tbody>");

            // $('#appointments').DataTable({
            //        "bFilter": false,
            //        "columnDefs": [
            //          { "orderable": true, "targets": 3 },
            //          { "orderable": true, "targets": 4 }
            //                ]
            //       });

}


function print(){
      $("#appointments tr").click(function() {
       let tabler=$(this).children("td").html();
       window.location.href="update.html?appointmentid="+tabler;
    });
}
var tableRow;
function findRow(){
      $("#appointments tr").click(function() {
       tableRow=$(this).children("td").html();
    });
}

function populatedepartmentDropdownA(specialties) {
    let dropdown = $('#departmentA');
    dropdown.prop('selectedIndex', 0);
    jQuery.each(specialties, function(i,department) {
     $("#departmentA").append("<option value="+department.name+">"+department.name+"</option>");
     });

}

function populatedepartmentDropdownS(specialties) {
    let dropdown = $('#departmentS');
    dropdown.prop('selectedIndex', 0);
    jQuery.each(specialties, function(i,department) {
     $("#departmentS").append("<option value="+department.name+">"+department.name+"</option>");
     });

}

function populatefacultysDropdown(facultys) {
         let dropdown= $('#facultyA');
         dropdown.prop('selectedIndex', 0);
         dropdown.empty();
         $("#facultyA").append("<option value='' selected disabled>Choose Faculty</option>");
         jQuery.each(facultys, function(i,faculty) {
           $("#facultyA").append("<option value="+faculty.id+">"+faculty.lastName+"</option>");
           });
}

function loadAppointment(id) {
    $.ajax({
        url: ROOT_PATH + "/appointment/" + id
    }).then(function(appointment) {
       $("input[name=departmentU]").val(appointment.faculty.department.name);
       $("input[name=facultyU]").val(appointment.faculty.lastName);
       let appointmentDay=appointment.dateTime.split(" ");
       $("input[name=dateU]").val(appointmentDay[0]);
       $("input[name=timeU]").val(appointmentDay[1]);
       $(":input[name=briefdescriptionU]").val(appointment.description);
       $(":input[name=notesU]").val(appointment.notes);
    });
};

$(document).ready(function() {
   let json = JSON.parse(sessionStorage.getItem(SESSION_STORAGE_LOGIN_TOKEN_NAME));
   let userw=json.userName;
   document.getElementById("welcome").innerHTML = "You are connected as " + userw;
     $.ajax({
        url: ROOT_PATH + "/students/user/"+userw
    }).then(function(student) {
        document.getElementById("name").innerHTML = student.firstName;
        document.getElementById("email").innerHTML = student.email;
    });
   $.ajax({
        url: ROOT_PATH + "/appointment/all/student"
    }).then(function(appointments) {
        populateDataTableAndUpdate(appointments);
    });

    $.ajax({
               url:ROOT_PATH + '/department/all',
               dataType : 'json',
               contentType: 'application/json',
           }).then(function(specialties) {
               populatedepartmentDropdownA(specialties);
               populatedepartmentDropdownS(specialties);

       });

     $('#departmentA').change(function(){
                let departmentName=$("#departmentA").val();
                $.ajax({
                       url:ROOT_PATH + '/doc/all/spec/'+ departmentName,
                       dataType : 'json',
                       contentType: 'application/json',
                      }).then(function(facultys) {
                          populatefacultysDropdown(facultys);
                  });
          });

    $("#saveButton").on('click', function(event){
        event.preventDefault();
        let url = new URL(document.URL);
        var c = url.searchParams.get("appointmentid");
        let dayU=$("input[name=dateU]").val();
        let timeU=$("input[name=timeU]").val();
        let newdate=dayU.concat(" ",timeU);
        let newdescription=$(":input[name=briefdescriptionU]").val();
        let newnotes=$(":input[name=notesU]").val();
        let updatedata = {
              "dateTime": newdate,
              "description": newdescription,
              "notes": newnotes
            };
         $.ajax({
             url:  ROOT_PATH + '/appointment/'+ c,
             type : 'PUT',
             data: JSON.stringify(updatedata),
             dataType : 'json',
             contentType: 'application/json',
             success: function(data){
               $("#updateModal").modal();
                },
                 statusCode: {
                     401 : function() {
                         alert("Invalid data!");
                     }
                 }
             });

    });



  $("#cancelButton").on('click', function(event){
       window.location.href="index.html";
  });

  $("#searchAppointmentButton").on('click', function(event){
          event.preventDefault();
          let departmentToSearch=$("#departmentS").val();
          let datesToSearch=$("input[name=dates]").val().split(" ");
          let startds = datesToSearch[0];
          let endds = datesToSearch[2];
           $.ajax({
               url:  ROOT_PATH + '/appointment/all/date-department?department='+departmentToSearch+'&startdate='+startds+" 00:00"+'&enddate='+endds+" 24:00",
               success: function(data){
                 $('#SearchModal').modal('hide');
                 populateDataTableAndUpdate(data);
                  },
                   statusCode: {
                       401 : function() {
                           alert("Invalid data!");
                       }
                   }
               });
   });

 $("#createAppointmentButton").on('click', function(event){
        event.preventDefault();
        let facultyA=$( "#facultyA" ).val();
        let dateA=$("input[name=date]").val();
        let timeA=$("input[name=time]").val();
        let dateTime=dateA.concat(" ",timeA);
        let description=$("#briefdescription").val();
        let  = $("#").val();
        let  = $("#age").val();
        let priority=$("#priority option:selected").val();
        //priority = priority ==='URGENT'?1:2;
        let notes=$("#notes").val();
        let dataAppointment = {
              "faculty":   {
                  "id": facultyA
              },
              "dateTime": dateTime,
              "description": description,
              "notes": notes,
              "priority":priority,
              "":,
              "age":age
            };
         $.ajax({
             url:  ROOT_PATH + '/appointment/new',
             type : 'POST',
             data: JSON.stringify(dataAppointment),
             dataType : 'json',
             contentType: 'application/json',
             success: function(data){
               $.notify("The appointment has been created! u will get the confirmation by email", "success");
               $('#makeAppointmentModal').modal('hide');
               let t=$("#appointments").DataTable();
              t.row.add( [data.id,data.faculty.department.name, data.dateTime,"<i id='ClickableImageEdit' class='fa fa-pencil-square-o' aria-hidden='true' onclick='print()'></i>","<i id='ClickableImageCancel' class='fa fa-ban' aria-hidden='true' data-toggle='modal' data-target='#deleteModal' onclick='findRow()'></i>"] ).node().id="appointmentRow"+data.id;
              t.draw();
                },
                 statusCode: {
                     401 : function() {
                         alert("Invalid data!");
                     }
                 }
             });
 });

    $("#deleteButton").on('click', function(event){
        event.preventDefault();
        $.ajax({
            url: ROOT_PATH + "/appointment/" + tableRow,
            type : "DELETE",
            dataType : 'json',
            contentType: 'application/json',
                success : function(result) {
                    $("#appointments").DataTable().row("#appointmentRow"+tableRow).remove().draw();

                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                    alert("Could not delete appointment!");
                 }
         })

    });
});
