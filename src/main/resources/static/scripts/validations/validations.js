$(document).ready(function () {
 inputValidation();
})
$(document).on('shown.bs.modal', function () {
 inputValidation();
})

function inputValidation() {
     var pswdconfirmation;
     var pswdStatus;
    $("form:not([data-custom-validation]) input,textarea,select").on("keyup change focus", function () {
        try {
            const closestForm = $(this).closest("form");
            if (!closestForm.length) {
                console.warn("Submit button is undefined.");
                return;
            }

            // Enable / Disable submit button
            let submitButton = $(closestForm).find("[type='button'],button");
            if (submitButton.length > 1) {
                  submitButton.splice(0, 1);
            }
           if (!submitButton.length) {
                console.warn("Submit button is undefined.");
                return;
            }


            const isFormValid = $(closestForm)[0].checkValidity();

            if ($('#rpwd').length) {
                $("#rpwd").on('keyup change focus', function (e) {
                    if ($('#pswd').val()==$('#rpwd').val()) {
                       pswdconfirmation=true;
                       let thisAlert = $('#rpwd').parent();
                       $(thisAlert).removeClass('alert-validate');
                       pswdStatus=(isFormValid && pswdconfirmation);
                        submitButton.attr("disabled", !pswdStatus);
                    } else{
                           pswdconfirmation=false;
                           let thisAlert = $('#rpwd').parent();
                           $(thisAlert).addClass('alert-validate');
                           pswdStatus=(isFormValid && pswdconfirmation);
                           submitButton.attr("disabled", !pswdStatus);
                        }
               });

               $("#pswd").on('keyup change', function (e) {
                    pswdconfirmation=false;
                    let Alertrpswd = $('#rpwd').parent();
                    $(Alertrpswd).addClass('alert-validate');
                     pswdStatus=(isFormValid && pswdconfirmation);
                    submitButton.attr("disabled", !pswdStatus);
               });

                    pswdStatus=(isFormValid && pswdconfirmation);
                    submitButton.attr("disabled", !pswdStatus);
            }
            else{
                 submitButton.attr("disabled", !isFormValid);

            }

            const isCurrentTargetValid = $(this).is(":valid");
            if (!isCurrentTargetValid) {
              let thisAlert = $(this).parent();
              $(thisAlert).addClass('alert-validate');
            } else {
                let thisAlert =  $(this).parent();
               $(thisAlert).removeClass('alert-validate');
            }
        }catch(e) {
            console.error(e);
        }
    });

}
