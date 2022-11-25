function register(firstnameElement,lastnameElement,amkaElement,phoneElement,emailElement,usernameElement,passwordElement) {
    event.preventDefault();
    let firstname = firstnameElement && firstnameElement.value ? firstnameElement.value : "";
    let lastname = lastnameElement && lastnameElement.value ? lastnameElement.value : "";
    let id = amkaElement && amkaElement.value ? amkaElement.value : "";
    let phone = phoneElement && phoneElement.value ? phoneElement.value : "";
    let email = emailElement && emailElement.value ? emailElement.value : "";
    let username = usernameElement && usernameElement.value ? usernameElement.value : "";
    let password = passwordElement && passwordElement.value ? passwordElement.value : "";


    var registrationdata = {
      "firstName" :  firstname,
      "lastName" :  lastname,
      "id" :  id,
      "phone" :  phone,
      "email" :  email,
      "username" :  username,
      "password" :  password
    };

    $.ajax({
    url:  ROOT_PATH + '/register',
    type : "POST",
    data: JSON.stringify(registrationdata),
    dataType : 'json',
    contentType: 'application/json',
    success: function(data){
           //alert("The registration completed successfully!");
           $.notify("The registration completed successfully!", "success");
            window.location.replace(ROOT_PATH + "/index.html");
       },
        statusCode: {
            409 : function(response) {
            $.notify(response.responseText, "error");
            //alert(response.responseText);

            }
        }
    });

}