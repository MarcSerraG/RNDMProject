
$(document).ready(function () {



    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    })
  

    var input = $('.validate-input .input100');

    recheck();

    function recheck(){
        var check = true;

        for(var i=0; i<input.length-1; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }

        return check;
    }

    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        var spanContent = IdToClass($(input).attr('id')).text();

        if(spanContent !=""){
            ConvertID($(input).attr('id'),spanContent);
           return false;
        }

    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(".username").text("");
        $(".email").text("");
        $(".password").text("");
        $(thisAlert).removeClass('alert-validate');
    }

    function IdToClass(idToConvert) {
        switch (idToConvert) {
            case 'username':
                var user = $('.username');
                return user;
                break;
            case 'email':
                var email = $('.email');
                return email;
                break;
            case 'password':
                var pass = $('.password');
                return pass;
                break;
        }
    }

    function ConvertID(idToConvert, message){
        switch (idToConvert) {
            case 'username':
                $('#username2').attr("data-validate", message);
                return;
                break;
            case 'email':
                $('#email2').attr("data-validate", message);
                return;
                break;
            case 'password':
                $('#password2').attr("data-validate", message);
                return;
                break;
        }
    }





    $("#repeatPassword").keyup(checkPasswordMatch);

    $("#password").keyup(IsEmptyRepeated);


    function IsEmptyRepeated() {
        $("#repeatPassword").parent().addClass('alert-validate');
    }


    function checkPasswordMatch() {
        var password = $("#password").val();
        var confirmPassword = $("#repeatPassword").val();

        if (password != confirmPassword)
            $("#repeatPassword").parent().addClass('alert-validate');
        else {
            $("#repeatPassword").parent().removeClass('alert-validate');
            $("#registerButton").prop('disabled', false);
        }
    }

});